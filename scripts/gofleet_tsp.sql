--Travelling Salesman Brute Force Solution
DROP FUNCTION IF EXISTS gofleet_tsp(TEXT, TEXT, TEXT, INTEGER) CASCADE;
DROP FUNCTION IF EXISTS gofleet_tsp(TEXT, TEXT, TEXT, INTEGER) CASCADE;
DROP FUNCTION IF EXISTS gofleet_tsp_next(TEXT, TEXT, TEXT, INTEGER) CASCADE;
DROP TYPE IF EXISTS gofleet_tsp_res_table;
CREATE TYPE gofleet_tsp_res_table as (
			  id BIGINT,
                          edge INTEGER,
			  the_geom GEOMETRY,
                          cost DOUBLE PRECISION
);

CREATE FUNCTION gofleet_tsp_next(rtable TEXT, ptable TEXT, gid TEXT, source INTEGER)
RETURNS INTEGER AS 
$BODY$
DECLARE
	answer INTEGER;
	r RECORD;
        BEGIN
            RAISE INFO 'gofleet_tsp_next(%, %, %, %)', rtable, ptable, gid, source;  
	    EXECUTE 'DROP TABLE IF EXISTS routing_next';
            EXECUTE 'CREATE TEMPORARY TABLE routing_next
			(
			  id BIGSERIAL NOT NULL,
                          edge INTEGER NOT NULL,
                          cost DOUBLE PRECISION,
                          CONSTRAINT routing_next_pk PRIMARY KEY (id)
                        )';
  
	    BEGIN
		FOR r IN EXECUTE 'SELECT DISTINCT id FROM ' || ptable || ' as p WHERE p.id <> ' || source ||
				' AND NOT EXISTS (SELECT 1 FROM routing_res as re where p.id =re.edge)' LOOP
		  RAISE DEBUG 'Calculado hacia %', r.id;
		  BEGIN
			INSERT INTO routing_next
				(edge, "cost")
				(select r.id, sum(cost) from 
					shortest_path_shooting_star('SELECT ' || gid || ' as id, 
								source, 
								target, 
								st_length(the_geom) as cost, 
								st_length(the_geom) as reverse_cost,
								x1,
								y1,
								x2,
								y2,
								rule,
								to_cost FROM ' || rtable, source, r.id :: integer, true, true));
		  EXCEPTION 
                        WHEN internal_error THEN 
			RAISE WARNING 'Error calculating route from % to %', source, r.id;
		  END;
		  END LOOP;
		RAISE DEBUG 'Calculado para %', source;
		FOR r IN SELECT DISTINCT edge, cost FROM routing_next ORDER BY cost ASC LOOP
			RAISE DEBUG 'Resultados cost % edge %', r.cost, r.edge;
		END LOOP;
                SELECT edge INTO answer FROM routing_next WHERE edge <> -1 ORDER BY cost ASC LIMIT 1;
		RAISE INFO 'Going to EDGE %', answer;   
              
                EXCEPTION 
                        WHEN internal_error THEN 
                        RAISE WARNING 'Error calculating next from edge %',source;
                END;
                RETURN answer;		
        END;
$BODY$ 
LANGUAGE 'plpgsql';

--Routing table, Stops table, ID column name, ID source
--The points table contains IDs from the routing table 
--where we need to stop
--Routing table contains all the pgrouting data
CREATE FUNCTION gofleet_tsp(rtable TEXT, ptable TEXT, gid TEXT, source INTEGER)
RETURNS setof gofleet_tsp_res_table AS 
$BODY$
DECLARE
	target INTEGER;
	done INTEGER;
	max INTEGER;
	source_ INTEGER;
	r RECORD;
        BEGIN
	    RAISE INFO 'gofleet_tsp(%, %, %, %)', rtable, ptable, gid, source_;
	    EXECUTE 'DROP TABLE IF EXISTS routing_res';
            EXECUTE 'CREATE TEMPORARY TABLE routing_res
			(
			  id BIGSERIAL NOT NULL,
                          edge INTEGER NOT NULL,
                          the_geom GEOMETRY,
                          cost DOUBLE PRECISION,
                          CONSTRAINT routing_res_pk PRIMARY KEY (id)
                        )';

            RAISE DEBUG 'Table routing_res created.';   
	    SELECT source INTO source_;
	    BEGIN
		SELECT COUNT(1) INTO done FROM routing_res as re WHERE 
					EXISTS (SELECT 1 FROM puntos p WHERE p.id = re.edge);
					
		SELECT COUNT(1) INTO max FROM puntos p;
		WHILE done < max LOOP
		  SELECT * INTO target FROM gofleet_tsp_next(rtable, ptable, gid, source_);
		  INSERT INTO routing_res
                        (edge, "cost")
                        (select edge_id, cost from 
			    shortest_path_shooting_star('SELECT ' || gid || ' as id, 
								source, 
								target, 
								st_length(the_geom) as cost, 
								st_length(the_geom) as reverse_cost,
								x1,
								y1,
								x2,
								y2,
								rule,
								to_cost FROM ' || rtable, source_, target, true, true));
		  SELECT target INTO source_;
		  SELECT COUNT(1) INTO done FROM routing_res as re WHERE 
					EXISTS (SELECT 1 FROM puntos p WHERE p.id = re.edge);

		  FOR r in SELECT * FROM routing_res LOOP
			RAISE INFO '%', r;
		  END LOOP;

		  END LOOP;
                EXCEPTION 
                        WHEN internal_error THEN 
                        RAISE WARNING 'Error calculating route from % to %',source_, target;
                END;

                EXECUTE 'UPDATE routing_res SET the_geom = (SELECT the_geom from ways where routing_res.edge = ways.gid)';

		RETURN QUERY EXECUTE 'SELECT * FROM routing_res ORDER BY id ASC';
        END;
$BODY$ 
LANGUAGE 'plpgsql';