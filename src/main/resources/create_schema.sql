--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: demogis; Type: DATABASE; Schema: -; Owner: -
--

CREATE USER demogis WITH PASSWORD 'demogis';

DROP DATABASE IF EXISTS demogis;
CREATE DATABASE demogis WITH TEMPLATE = template_postgis ENCODING = 'UTF8' OWNER = demogis;

\connect demogis demogis

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: bandeja_entrada; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE bandeja_entrada (
    x_bandeja_entrada bigint NOT NULL,
    origen character varying(255),
    datagrama_tetra character varying(255),
    marca_temporal timestamp without time zone,
    procesado boolean DEFAULT false
);


--
-- Name: bandeja_salida; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE bandeja_salida (
    x_bandeja bigint NOT NULL,
    datagrama_tetra character varying(255),
    marca_temporal_tx timestamp without time zone,
    prioridad integer,
    procesado character varying(255) DEFAULT false,
    destino character varying(10) DEFAULT 0 NOT NULL,
    tipo integer DEFAULT (-1) NOT NULL
);
--
-- Name: c3p0_test_table; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE c3p0_test_table (
    a character(1)
);


--
-- Name: capa; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE capa (
    x_capa bigint NOT NULL,
    fk_capas_informacion bigint NOT NULL,
    nombre character varying(50) DEFAULT ''::character varying NOT NULL,
    estilo character varying(50),
    orden integer NOT NULL
);


--
-- Name: capas_informacion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE capas_informacion (
    x_capa_informacion bigint NOT NULL,
    nombre character varying(50),
    url character varying(2500),
    updated_at timestamp without time zone DEFAULT now(),
    opcional boolean DEFAULT true,
    orden smallint DEFAULT 0,
    info_adicional character varying(256),
    habilitada boolean DEFAULT true NOT NULL,
    url_visible character varying NOT NULL
);


--
-- Name: categoria_incidencias; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE categoria_incidencias (
    x_categoria integer NOT NULL,
    ident character varying(15) DEFAULT ''::character varying NOT NULL,
    descripcion character varying(150) DEFAULT ''::character varying NOT NULL
);


-- Name: clientes_conectados; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE clientes_conectados (
    ultima_conexion timestamp without time zone DEFAULT now() NOT NULL,
    x_cliente bigint NOT NULL,
    fk_usuario bigint NOT NULL
);


--
-- Name: configuracion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE configuracion (
    identificador character varying(20) DEFAULT 'DEFAULT'::character varying NOT NULL,
    valor character varying,
    updatable boolean DEFAULT false NOT NULL
);


--
-- Name: TABLE configuracion; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE configuracion IS 'Tabla de configuracion';


--
-- Name: estado_incidencias; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE estado_incidencias (
    id integer NOT NULL,
    identificador character varying DEFAULT ''::character varying NOT NULL
);


--
-- Name: estado_recursos; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE estado_recursos (
    id integer NOT NULL,
    identificador character varying(255) DEFAULT ''::character varying NOT NULL
);


--
-- Name: flotas; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE flotas (
    x_flota bigint NOT NULL,
    nombre character varying(50) NOT NULL,
    juego_iconos character varying(20),
    updated_at timestamp without time zone DEFAULT now(),
    info_adicional character varying(256),
    habilitado boolean DEFAULT true NOT NULL
);

SET default_with_oids = false;

--
-- Name: historico_gps; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE historico_gps (
    x_historico bigint NOT NULL,
    geom geometry,
    pos_x numeric(15,9) NOT NULL,
    pos_y numeric(15,8) NOT NULL,
    marca_temporal timestamp without time zone NOT NULL,
    recurso character varying(50),
    subflota character varying(50),
    timestamp_db timestamp without time zone DEFAULT now(),
    tipo_recurso character varying(3)
);


--
-- Name: incidencias; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE incidencias (
    x_incidencia bigint NOT NULL,
    fk_estado smallint NOT NULL,
    prioridad smallint,
    titulo character varying(100) DEFAULT ''::character varying NOT NULL,
    fecha_creacion timestamp without time zone,
    fecha_cierre timestamp without time zone,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    descripcion character varying DEFAULT ''::character varying NOT NULL,
    fk_usuario bigint,
    fk_categoria bigint,
    geom geometry
);


--
-- Name: TABLE incidencias; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE incidencias IS 'Avisos procedentes de Eurocop.';



--
-- Name: patrullas; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE patrullas (
    x_patrulla bigint NOT NULL,
    nombre character varying(50) NOT NULL,
    info_adicional character varying(256),
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: recursos; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE recursos (
    x_recurso bigint NOT NULL,
    tipo character varying(255),
    identificador character varying(255),
    nombre character varying(255),
    dispositivo integer,
    habilitado boolean,
    gestor character varying(255),
    funciones_eurocop character varying(255),
    fecha_cambio_eurocop timestamp without time zone,
    info_adicional character varying(255),
    flota_x_flota bigint,
    incidencia_x_incidencia bigint,
    patrulla_x_patrulla bigint,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    mal_asignado boolean DEFAULT false NOT NULL,
    fk_estado bigint,
    fk_historico_gps bigint
);


--
-- Name: roles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE roles (
    x_rol bigint NOT NULL,
    nombre character varying(50) NOT NULL,
    updated_at timestamp without time zone DEFAULT now(),
    info_adicional character varying(256)
);


--
-- Name: routing; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE routing (
    x_routing bigint NOT NULL,
    the_geom geometry,
    source integer,
    target integer,
    cost double precision DEFAULT (-1) NOT NULL,
    reverse_cost double precision DEFAULT (-1) NOT NULL,
    to_cost double precision,
    rule text,
    id bigint DEFAULT (-1) NOT NULL,
    name character varying,
    CONSTRAINT "check_dosDimensiones" CHECK ((ndims(the_geom) = 2)),
    CONSTRAINT geometry_valid_check CHECK (st_isvalid(the_geom))
);


--
-- Name: street; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE street (
    x_street bigint NOT NULL,
    tipoviaine integer,
    codigoine integer,
    nombreviaine character varying(255),
    estado character varying(255),
    revest character varying(255),
    length_ real,
    the_geom geometry,
    centroid geometry,
    sentido text,
    CONSTRAINT "centroidValido" CHECK (st_isvalid(centroid)),
    CONSTRAINT dos_dimensiones CHECK ((ndims(the_geom) = 2)),
    CONSTRAINT espunto CHECK (((geometrytype(centroid) = 'POINT'::text) OR (centroid IS NULL))),
    CONSTRAINT geometry_valid_check CHECK (st_isvalid(the_geom))
);


--
-- Name: TABLE street; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE street IS 'Callejero.';


--
-- Name: tipo_mensaje; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE tipo_mensaje (
    x_tipo_mensaje integer NOT NULL,
    nombre character varying(30) DEFAULT ''::character varying NOT NULL,
    prioridad bigint DEFAULT (-1) NOT NULL,
    codigo bigint DEFAULT (-1) NOT NULL,
    tipo_tetra integer DEFAULT (-1) NOT NULL
);


--
-- Name: usuarios; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE usuarios (
    x_usuarios bigint NOT NULL,
    nombre_usuario character varying(20) NOT NULL,
    password character(32),
    nombre character varying(50),
    habilitado boolean DEFAULT true,
    administrador boolean DEFAULT false,
    vehiculos_visibles boolean DEFAULT true,
    personas_visibles boolean DEFAULT true,
    incidencias_visibles boolean DEFAULT true,
    updated_at timestamp without time zone DEFAULT now(),
    fk_roles bigint,
    info_adicional character varying(256),
    apellidos character varying(50)
);


--
-- Name: usuarios_x_capas_informacion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE usuarios_x_capas_informacion (
    fk_usuarios bigint NOT NULL,
    fk_capa_informacion bigint NOT NULL,
    visible_historico boolean DEFAULT true NOT NULL,
    visible_gps boolean DEFAULT true NOT NULL
);


--
-- Name: vertices_tmp; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vertices_tmp (
    id integer NOT NULL,
    the_geom geometry,
    CONSTRAINT enforce_dims_the_geom CHECK ((ndims(the_geom) = 2)),
    CONSTRAINT enforce_geotype_the_geom CHECK (((geometrytype(the_geom) = 'POINT'::text) OR (the_geom IS NULL))),
    CONSTRAINT enforce_srid_the_geom CHECK ((srid(the_geom) = 4326))
);


--
-- Name: ways; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE ways (
    gid integer NOT NULL,
    class_id integer,
    length double precision,
    name character(200),
    x1 double precision,
    y1 double precision,
    x2 double precision,
    y2 double precision,
    reverse_cost double precision,
    rule text,
    to_cost double precision,
    the_geom geometry,
    source integer,
    target integer,
    CONSTRAINT enforce_dims_the_geom CHECK ((ndims(the_geom) = 2)),
    CONSTRAINT enforce_geotype_the_geom CHECK (((geometrytype(the_geom) = 'MULTILINESTRING'::text) OR (the_geom IS NULL))),
    CONSTRAINT enforce_srid_the_geom CHECK ((srid(the_geom) = 4326))
);

--
-- Name: cleanbandejaentrada(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION cleanbandejaentrada() RETURNS trigger
    AS $$
    BEGIN
       DELETE FROM bandeja_entrada WHERE marca_temporal < (now() - INTERVAL '1 month');
       RETURN NULL;
    END;
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_random_timestamp()
  RETURNS trigger AS
$BODY$
BEGIN
	EXECUTE 'update '||TG_RELNAME||' set updated_at = now() where updated_at in (select updated_at from '||TG_RELNAME||' limit 1)';
	return null;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

CREATE OR REPLACE FUNCTION update_changetimestamp_column()
  RETURNS trigger AS
$BODY$
BEGIN
   NEW.updated_at = now(); 
   RETURN NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE FUNCTION update_changetimestamp_column_recursos()
  RETURNS trigger AS
$BODY$
  DECLARE
  BEGIN
	IF NEW.fk_historico_gps = OLD.fk_historico_gps THEN
		NEW.updated_at = now(); 
	ELSE 
		IF NEW.info_adicional <> OLD.info_adicional
			OR NEW.tipo <> OLD.tipo 
			OR NEW.dispositivo <> OLD.dispositivo
			OR NEW.habilitado <> OLD.habilitado
			OR NEW.flota_x_flota <> OLD.flota_x_flota
			OR NEW.incidencia_x_incidencia <> OLD.incidencia_x_incidencia
			OR NEW.patrulla_x_patrulla <> OLD.patrulla_x_patrulla THEN
			NEW.updated_at = now();
		END IF;
	END IF;
	RETURN NEW;
  END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


SET search_path = public, pg_catalog;

--
-- Name: bandeja_entrada_x_bandeja_entrada_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE bandeja_entrada_x_bandeja_entrada_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1
    CYCLE;


--
-- Name: bandeja_entrada_x_bandeja_entrada_seq1; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE bandeja_entrada_x_bandeja_entrada_seq1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bandeja_entrada_x_bandeja_entrada_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE bandeja_entrada_x_bandeja_entrada_seq1 OWNED BY bandeja_entrada.x_bandeja_entrada;


--
-- Name: bandeja_salida_x_bandeja_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE bandeja_salida_x_bandeja_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1
    CYCLE;


--
-- Name: bandeja_salida_x_bandeja_seq1; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE bandeja_salida_x_bandeja_seq1
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bandeja_salida_x_bandeja_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE bandeja_salida_x_bandeja_seq1 OWNED BY bandeja_salida.x_bandeja;


--
-- Name: capa_x_capa_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE capa_x_capa_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: capas_informacion_x_capa_informacion_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE capas_informacion_x_capa_informacion_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: capas_informacion_x_capa_informacion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE capas_informacion_x_capa_informacion_seq OWNED BY capas_informacion.x_capa_informacion;


--
-- Name: categoria_incidencias_x_categoria_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE categoria_incidencias_x_categoria_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: categoria_incidencias_x_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE categoria_incidencias_x_categoria_seq OWNED BY categoria_incidencias.x_categoria;


--
-- Name: estado_incidencias_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE estado_incidencias_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: estado_incidencias_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE estado_incidencias_id_seq OWNED BY estado_incidencias.id;


--
-- Name: estado_recursos_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE estado_recursos_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: estado_recursos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE estado_recursos_id_seq OWNED BY estado_recursos.id;


--
-- Name: flotas_x_flota_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE flotas_x_flota_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: flotas_x_flota_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE flotas_x_flota_seq OWNED BY flotas.x_flota;


--
-- Name: historico_gps_x_historico_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE historico_gps_x_historico_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: historico_gps_x_historico_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE historico_gps_x_historico_seq OWNED BY historico_gps.x_historico;


--
-- Name: incidencias_x_incidencia_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE incidencias_x_incidencia_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: incidencias_x_incidencia_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE incidencias_x_incidencia_seq OWNED BY incidencias.x_incidencia;


--
-- Name: patrullas_x_patrulla_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE patrullas_x_patrulla_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: patrullas_x_patrulla_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE patrullas_x_patrulla_seq OWNED BY patrullas.x_patrulla;


--
-- Name: recursos_x_recurso_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE recursos_x_recurso_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: roles_x_flotas_x_flota_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE roles_x_flotas_x_flota_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


CREATE TABLE roles_x_flotas
(
  x_rol bigint NOT NULL,
  x_flota bigserial NOT NULL
);

--
-- Name: roles_x_flotas_x_flota_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE roles_x_flotas_x_flota_seq OWNED BY roles_x_flotas.x_flota;


--
-- Name: roles_x_rol_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE roles_x_rol_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: roles_x_rol_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE roles_x_rol_seq OWNED BY roles.x_rol;


--
-- Name: street_x_street_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE street_x_street_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: street_x_street_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE street_x_street_seq OWNED BY street.x_street;


--
-- Name: tipo_mensaje_x_tipo_mensaje_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE tipo_mensaje_x_tipo_mensaje_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: tipo_mensaje_x_tipo_mensaje_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE tipo_mensaje_x_tipo_mensaje_seq OWNED BY tipo_mensaje.x_tipo_mensaje;


--
-- Name: usuarios_x_usuarios_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE usuarios_x_usuarios_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: usuarios_x_usuarios_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE usuarios_x_usuarios_seq OWNED BY usuarios.x_usuarios;


--
-- Name: vertices_tmp_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE vertices_tmp_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: vertices_tmp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE vertices_tmp_id_seq OWNED BY vertices_tmp.id;


--
-- Name: x_bandeja_entrada; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE bandeja_entrada ALTER COLUMN x_bandeja_entrada SET DEFAULT nextval('bandeja_entrada_x_bandeja_entrada_seq1'::regclass);


--
-- Name: x_bandeja; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE bandeja_salida ALTER COLUMN x_bandeja SET DEFAULT nextval('bandeja_salida_x_bandeja_seq1'::regclass);


--
-- Name: x_capa_informacion; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE capas_informacion ALTER COLUMN x_capa_informacion SET DEFAULT nextval('capas_informacion_x_capa_informacion_seq'::regclass);


--
-- Name: x_categoria; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE categoria_incidencias ALTER COLUMN x_categoria SET DEFAULT nextval('categoria_incidencias_x_categoria_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE estado_incidencias ALTER COLUMN id SET DEFAULT nextval('estado_incidencias_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE estado_recursos ALTER COLUMN id SET DEFAULT nextval('estado_recursos_id_seq'::regclass);


--
-- Name: x_flota; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE flotas ALTER COLUMN x_flota SET DEFAULT nextval('flotas_x_flota_seq'::regclass);


--
-- Name: x_historico; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE historico_gps ALTER COLUMN x_historico SET DEFAULT nextval('historico_gps_x_historico_seq'::regclass);


--
-- Name: x_incidencia; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE incidencias ALTER COLUMN x_incidencia SET DEFAULT nextval('incidencias_x_incidencia_seq'::regclass);


--
-- Name: x_patrulla; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE patrullas ALTER COLUMN x_patrulla SET DEFAULT nextval('patrullas_x_patrulla_seq'::regclass);


--
-- Name: x_rol; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE roles ALTER COLUMN x_rol SET DEFAULT nextval('roles_x_rol_seq'::regclass);


--
-- Name: x_flota; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE roles_x_flotas ALTER COLUMN x_flota SET DEFAULT nextval('roles_x_flotas_x_flota_seq'::regclass);


--
-- Name: x_street; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE street ALTER COLUMN x_street SET DEFAULT nextval('street_x_street_seq'::regclass);


--
-- Name: x_tipo_mensaje; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE tipo_mensaje ALTER COLUMN x_tipo_mensaje SET DEFAULT nextval('tipo_mensaje_x_tipo_mensaje_seq'::regclass);


--
-- Name: x_usuarios; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE usuarios ALTER COLUMN x_usuarios SET DEFAULT nextval('usuarios_x_usuarios_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE vertices_tmp ALTER COLUMN id SET DEFAULT nextval('vertices_tmp_id_seq'::regclass);


--
-- Name: bandeja_entrada_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bandeja_entrada
    ADD CONSTRAINT bandeja_entrada_pkey PRIMARY KEY (x_bandeja_entrada);


--
-- Name: bandeja_salida_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bandeja_salida
    ADD CONSTRAINT bandeja_salida_pkey PRIMARY KEY (x_bandeja);


--
-- Name: categoria_incidencias_ident_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY categoria_incidencias
    ADD CONSTRAINT categoria_incidencias_ident_key UNIQUE (ident);


--
-- Name: categoria_incidencias_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY categoria_incidencias
    ADD CONSTRAINT categoria_incidencias_pkey PRIMARY KEY (x_categoria);


--
-- Name: configuracion_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY configuracion
    ADD CONSTRAINT configuracion_pkey PRIMARY KEY (identificador);


--
-- Name: constraint_clientes_conectados; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY clientes_conectados
    ADD CONSTRAINT constraint_clientes_conectados PRIMARY KEY (x_cliente);


--
-- Name: estado_incidencias_identificador_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY estado_incidencias
    ADD CONSTRAINT estado_incidencias_identificador_key UNIQUE (identificador);


--
-- Name: estado_incidencias_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY estado_incidencias
    ADD CONSTRAINT estado_incidencias_pkey PRIMARY KEY (id);


--
-- Name: estado_recursos_identificador_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY estado_recursos
    ADD CONSTRAINT estado_recursos_identificador_key UNIQUE (identificador);


--
-- Name: estado_recursos_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY estado_recursos
    ADD CONSTRAINT estado_recursos_pkey PRIMARY KEY (id);


--
-- Name: pk_capas_informacion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY capas_informacion
    ADD CONSTRAINT pk_capas_informacion PRIMARY KEY (x_capa_informacion);


--
-- Name: pk_flotas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY flotas
    ADD CONSTRAINT pk_flotas PRIMARY KEY (x_flota);


--
-- Name: pk_historico_gps; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY historico_gps
    ADD CONSTRAINT pk_historico_gps PRIMARY KEY (x_historico);


--
-- Name: pk_incidencias; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY incidencias
    ADD CONSTRAINT pk_incidencias PRIMARY KEY (x_incidencia);


--
-- Name: pk_patrullas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY patrullas
    ADD CONSTRAINT pk_patrullas PRIMARY KEY (x_patrulla);


--
-- Name: pk_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT pk_roles PRIMARY KEY (x_rol);


--
-- Name: pk_roles_x_flotas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY roles_x_flotas
    ADD CONSTRAINT pk_roles_x_flotas PRIMARY KEY (x_rol, x_flota);


--
-- Name: pk_street; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY street
    ADD CONSTRAINT pk_street PRIMARY KEY (x_street);


--
-- Name: pk_usuarios; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT pk_usuarios PRIMARY KEY (x_usuarios);


--
-- Name: pkey_capa; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY capa
    ADD CONSTRAINT pkey_capa PRIMARY KEY (x_capa);


--
-- Name: pkey_tipo_mensaje; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tipo_mensaje
    ADD CONSTRAINT pkey_tipo_mensaje PRIMARY KEY (x_tipo_mensaje);


--
-- Name: pkey_usuarios_x_capas_informacion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY usuarios_x_capas_informacion
    ADD CONSTRAINT pkey_usuarios_x_capas_informacion PRIMARY KEY (fk_usuarios, fk_capa_informacion);


--
-- Name: recursos_dispositivo_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT recursos_dispositivo_key UNIQUE (dispositivo);


--
-- Name: recursos_identificador_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT recursos_identificador_key UNIQUE (identificador);


--
-- Name: recursos_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT recursos_pkey PRIMARY KEY (x_recurso);


--
-- Name: routing_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY routing
    ADD CONSTRAINT routing_pkey PRIMARY KEY (x_routing);

--
-- Name: tipo_mensaje_codigo_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tipo_mensaje
    ADD CONSTRAINT tipo_mensaje_codigo_key UNIQUE (codigo);


--
-- Name: uq_flotas_nombre; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY flotas
    ADD CONSTRAINT uq_flotas_nombre UNIQUE (nombre);


--
-- Name: uq_roles_nombre; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT uq_roles_nombre UNIQUE (nombre);


--
-- Name: uq_usuarios_nombre_usuario; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT uq_usuarios_nombre_usuario UNIQUE (nombre_usuario);


--
-- Name: ways_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ways
    ADD CONSTRAINT ways_pkey PRIMARY KEY (gid);


--
-- Name: fk_; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fk_ ON usuarios USING btree (fk_roles);


--
-- Name: fki_; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_ ON recursos USING btree (flota_x_flota);


--
-- Name: geom_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX geom_idx ON routing USING gist (the_geom);


--
-- Name: idx_capas_informacion_nombre; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_capas_informacion_nombre ON capas_informacion USING btree (nombre);


--
-- Name: idx_flota_historico_gps; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_flota_historico_gps ON historico_gps USING btree (subflota);


--
-- Name: idx_flotas_nombre; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_flotas_nombre ON flotas USING btree (nombre);


--
-- Name: idx_historico_gps_geom; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_historico_gps_geom ON historico_gps USING gist (geom);


--
-- Name: idx_marca_temporal; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_marca_temporal ON historico_gps USING btree (marca_temporal);


--
-- Name: idx_nombre; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_nombre ON capas_informacion USING btree (nombre);


--
-- Name: idx_nombre_usuario_password; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_nombre_usuario_password ON usuarios USING btree (nombre_usuario, password);


--
-- Name: idx_recurso_dispositivo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_recurso_dispositivo ON recursos USING btree (dispositivo);


--
-- Name: idx_recurso_identificador; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_recurso_identificador ON recursos USING btree (identificador);


--
-- Name: idx_recurso_nombre; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_recurso_nombre ON recursos USING btree (nombre);


--
-- Name: idx_recurso_on_historico_gps; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_recurso_on_historico_gps ON historico_gps USING btree (recurso);


--
-- Name: idx_roles_nombre; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_roles_nombre ON roles USING btree (nombre);


--
-- Name: idx_routing_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_routing_name ON routing USING hash (name);


--
-- Name: idx_street_centroid; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_street_centroid ON street USING gist (centroid);


--
-- Name: idx_street_codigoine; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_street_codigoine ON street USING btree (codigoine);


--
-- Name: idx_street_nombreviaine; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_street_nombreviaine ON street USING btree (nombreviaine);


--
-- Name: idx_street_the_geom; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_street_the_geom ON street USING gist (the_geom);


--
-- Name: idx_usuarios_nombre_usuario; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_usuarios_nombre_usuario ON usuarios USING btree (nombre_usuario);


--
-- Name: source_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX source_idx ON routing USING btree (source);


--
-- Name: target_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX target_idx ON routing USING btree (target);


--
-- Name: vertices_tmp_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX vertices_tmp_idx ON vertices_tmp USING gist (the_geom);


--
-- Name: capa_deleted_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER capa_deleted_at
    AFTER DELETE ON capas_informacion
    FOR EACH STATEMENT
    EXECUTE PROCEDURE update_random_timestamp();


--
-- Name: capa_updatedat; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER capa_updatedat
    BEFORE INSERT OR UPDATE ON capas_informacion
    FOR EACH ROW
    EXECUTE PROCEDURE update_changetimestamp_column();


--
-- Name: flotas_deleted_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER flotas_deleted_at
    AFTER DELETE ON flotas
    FOR EACH STATEMENT
    EXECUTE PROCEDURE update_random_timestamp();


--
-- Name: flotas_updatedat; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER flotas_updatedat
    BEFORE INSERT OR UPDATE ON flotas
    FOR EACH ROW
    EXECUTE PROCEDURE update_changetimestamp_column();


--
-- Name: incidencias_updatedat; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER incidencias_updatedat
    BEFORE INSERT OR UPDATE ON incidencias
    FOR EACH ROW
    EXECUTE PROCEDURE update_changetimestamp_column();


--
-- Name: patrullas_deleted_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER patrullas_deleted_at
    AFTER DELETE ON patrullas
    FOR EACH STATEMENT
    EXECUTE PROCEDURE update_random_timestamp();


--
-- Name: patrullas_updatedat; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER patrullas_updatedat
    BEFORE INSERT OR UPDATE ON patrullas
    FOR EACH ROW
    EXECUTE PROCEDURE update_changetimestamp_column();


--
-- Name: recurso_deleted_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER recurso_deleted_at
    AFTER DELETE ON recursos
    FOR EACH STATEMENT
    EXECUTE PROCEDURE update_random_timestamp();


--
-- Name: recursos_updatedat; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER recursos_updatedat
    BEFORE UPDATE ON recursos
    FOR EACH ROW
    EXECUTE PROCEDURE update_changetimestamp_column_recursos();


--
-- Name: roles_deleted_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER roles_deleted_at
    AFTER DELETE ON roles
    FOR EACH STATEMENT
    EXECUTE PROCEDURE update_random_timestamp();


--
-- Name: roles_updatedat; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER roles_updatedat
    BEFORE INSERT OR UPDATE ON roles
    FOR EACH ROW
    EXECUTE PROCEDURE update_changetimestamp_column();


--
-- Name: usuarios_deleted_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER usuarios_deleted_at
    AFTER DELETE ON usuarios
    FOR EACH STATEMENT
    EXECUTE PROCEDURE update_random_timestamp();


--
-- Name: usuarios_updatedat; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER usuarios_updatedat
    BEFORE INSERT OR UPDATE ON usuarios
    FOR EACH ROW
    EXECUTE PROCEDURE update_changetimestamp_column();


--
-- Name: clientes_conectados_fk_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY clientes_conectados
    ADD CONSTRAINT clientes_conectados_fk_usuario_fkey FOREIGN KEY (fk_usuario) REFERENCES usuarios(x_usuarios) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_capa_x_capa_informacion; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY capa
    ADD CONSTRAINT fk_capa_x_capa_informacion FOREIGN KEY (fk_capas_informacion) REFERENCES capas_informacion(x_capa_informacion) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_roles_x_flotas_flotas; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY roles_x_flotas
    ADD CONSTRAINT fk_roles_x_flotas_flotas FOREIGN KEY (x_flota) REFERENCES flotas(x_flota) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_roles_x_flotas_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY roles_x_flotas
    ADD CONSTRAINT fk_roles_x_flotas_roles FOREIGN KEY (x_rol) REFERENCES roles(x_rol) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_usuarios_x_capas_informacion_capas_informacion; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuarios_x_capas_informacion
    ADD CONSTRAINT fk_usuarios_x_capas_informacion_capas_informacion FOREIGN KEY (fk_capa_informacion) REFERENCES capas_informacion(x_capa_informacion) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_usuarios_x_capas_informacion_usuarios; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuarios_x_capas_informacion
    ADD CONSTRAINT fk_usuarios_x_capas_informacion_usuarios FOREIGN KEY (fk_usuarios) REFERENCES usuarios(x_usuarios) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkd0b16f4ae88c68ba; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT fkd0b16f4ae88c68ba FOREIGN KEY (patrulla_x_patrulla) REFERENCES patrullas(x_patrulla);


--
-- Name: incidencias_fk_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY incidencias
    ADD CONSTRAINT incidencias_fk_categoria_fkey FOREIGN KEY (fk_categoria) REFERENCES categoria_incidencias(x_categoria);


--
-- Name: incidencias_fk_estado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY incidencias
    ADD CONSTRAINT incidencias_fk_estado_fkey FOREIGN KEY (fk_estado) REFERENCES estado_incidencias(id);


--
-- Name: incidencias_fk_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY incidencias
    ADD CONSTRAINT incidencias_fk_usuario_fkey FOREIGN KEY (fk_usuario) REFERENCES usuarios(x_usuarios) ON DELETE SET NULL;


--
-- Name: recursos_fk_estado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT recursos_fk_estado_fkey FOREIGN KEY (fk_estado) REFERENCES estado_recursos(id);


--
-- Name: recursos_fk_historico_gps_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT recursos_fk_historico_gps_fkey FOREIGN KEY (fk_historico_gps) REFERENCES historico_gps(x_historico) ON DELETE SET NULL;


--
-- Name: recursos_flota_x_flota_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT recursos_flota_x_flota_fkey FOREIGN KEY (flota_x_flota) REFERENCES flotas(x_flota);


--
-- Name: recursos_incidencia_x_incidencia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY recursos
    ADD CONSTRAINT recursos_incidencia_x_incidencia_fkey FOREIGN KEY (incidencia_x_incidencia) REFERENCES incidencias(x_incidencia) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: usuarios_fk_roles_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_fk_roles_fkey FOREIGN KEY (fk_roles) REFERENCES roles(x_rol) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

