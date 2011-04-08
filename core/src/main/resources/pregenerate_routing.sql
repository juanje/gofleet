DROP FUNCTION IF EXISTS _pregenera_routing(TEXT, integer, integer);

CREATE FUNCTION _pregenera_routing(routing_table TEXT,  source integer, target integer)
RETURNS BOOLEAN AS
$BODY$
        BEGIN
		BEGIN
		INSERT INTO routing_pregenerated
			(vertex_id, edge_id, "cost")
			(SELECT vertex_id, edge_id, "cost"
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
			   source,
			   target,
			   true,
			   true));
		EXCEPTION 
			WHEN internal_error THEN 
			RAISE WARNING 'Error calculating route from (%) to (%)',source,target;
		END;
	RETURN TRUE;
        END;
$BODY$ 
LANGUAGE 'plpgsql';

DROP FUNCTION IF EXISTS pregenera_routing(TEXT, TEXT);

CREATE FUNCTION pregenera_routing(rtable TEXT, gid TEXT)
RETURNS BOOLEAN AS 
$BODY$
	DECLARE
		_row RECORD;
        BEGIN
            EXECUTE 'DROP TABLE IF EXISTS ' || rtable || '_pregenerated';
	    EXECUTE 'CREATE TABLE ' || rtable || '_pregenerated
			(
			  id bigserial NOT NULL,
			  vertex_id INTEGER,
			  edge_id INTEGER,
			  "cost" DOUBLE PRECISION,
			  CONSTRAINT routing_pregenerated_key PRIMARY KEY (id)
			)';

	    RAISE NOTICE 'Table pregenerated created.';   
		
            EXECUTE 'SELECT _pregenera_routing(''' || rtable || ''' :: TEXT, 
						s.' || gid || ' :: INTEGER, 
						t.' || gid || ' :: INTEGER)
		    FROM ' || rtable || ' AS s CROSS JOIN ' || rtable || ' AS t 
		    WHERE s.' || gid || ' <> t.' || gid || '';
	    RAISE NOTICE 'Pregenerated process completed sucessfully.';   
	    RETURN TRUE; 
        END;
$BODY$ 
LANGUAGE 'plpgsql';
