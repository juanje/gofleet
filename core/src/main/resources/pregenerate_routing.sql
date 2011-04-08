DROP FUNCTION IF EXISTS _pregenera_routing(TEXT, integer, integer);

--"private" function which generates the routing cache 
--from source to target using routing_table
CREATE FUNCTION _pregenera_routing(routing_table TEXT,  s integer, t integer)
RETURNS BOOLEAN AS
$BODY$
        BEGIN
		BEGIN
		INSERT INTO routing_pregenerated
			(source, target, vertex_id, edge_id, "cost")
			(SELECT s as source, t as target, vertex_id, edge_id, "cost"
				from shortest_path_shooting_star( 
			'select id as id, 
				source::integer, 
				target::integer, 
				ST_X(ST_StartPoint(the_geom)) as x1,
				ST_Y(ST_StartPoint(the_geom)) as y1,
				ST_X(ST_EndPoint(the_geom)) as x2,
				ST_Y(ST_EndPoint(the_geom)) as y2,
				rule,
				st_length(the_geom)::double precision as cost,
				st_length(the_geom)::double precision as to_cost,
				st_length(the_geom)::double precision as reverse_cost
				from ' || routing_table,
			   s,
			   t,
			   true,
			   true));
		EXCEPTION 
			WHEN internal_error THEN 
			RAISE WARNING 'Error calculating route from (%) to (%)',s,t;
		END;
	RETURN TRUE;
        END;
$BODY$ 
LANGUAGE 'plpgsql';

DROP FUNCTION IF EXISTS pregenera_routing(TEXT, TEXT, TEXT);

--Routing table, Points table, ID column
--The points table contains a reference to gid(routing) from/to
--which we want to pregenerate routing
--Routing table contains all the pgrouting data
CREATE FUNCTION pregenera_routing(rtable TEXT, ptable TEXT, gid TEXT)
RETURNS BOOLEAN AS 
$BODY$
        BEGIN
            EXECUTE 'DROP TABLE IF EXISTS ' || rtable || '_pregenerated';
	    EXECUTE 'CREATE TABLE ' || rtable || '_pregenerated
			(
			  id bigserial NOT NULL,
			  source INTEGER NOT NULL,
			  target INTEGER NOT NULL,
			  vertex_id INTEGER NOT NULL,
			  edge_id INTEGER NOT NULL,
			  "cost" DOUBLE PRECISION NOT NULL,
			  CONSTRAINT routing_pregenerated_key PRIMARY KEY (id)
			)';

	    RAISE NOTICE 'Table pregenerated created.';   
		
            EXECUTE 'SELECT _pregenera_routing(''' || rtable || ''' :: TEXT, 
						s.' || gid || ' :: INTEGER, 
						t.' || gid || ' :: INTEGER)
		    FROM ' || ptable || ' AS s CROSS JOIN ' || ptable || ' AS t 
		    WHERE s.' || gid || ' <> t.' || gid || '';
	    RAISE NOTICE 'Pregenerated process completed sucessfully.';   
	    RETURN TRUE; 
        END;
$BODY$ 
LANGUAGE 'plpgsql';
