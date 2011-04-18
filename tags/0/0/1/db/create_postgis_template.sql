DROP DATABASE IF EXISTS template_postgis;
CREATE DATABASE template_postgis WITH TEMPLATE = template1 ENCODING = 'UTF8';
UPDATE pg_database SET datistemplate = TRUE WHERE datname ='template_postgis';
CREATE LANGUAGE plpgsql;

--Maybe these are not the real routes (Debian Squeeze)
---PostGIS
\i /usr/share/postgresql/8.4/contrib/postgis-1.5/postgis.sql
\i /usr/share/postgresql/8.4/contrib/postgis-1.5/spatial_ref_sys.sql
---PgRoutingCore
--\i /opt/pgrouting1.03/core/sql/routing_core.sql
--\i /opt/pgrouting1.03/core/sql/routing_core_wrappers.sql

GRANT ALL ON geometry_columns TO PUBLIC;
GRANT ALL ON spatial_ref_sys TO PUBLIC;
VACUUM FREEZE;
