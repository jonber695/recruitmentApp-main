

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.5 (Ubuntu 13.5-0ubuntu0.21.04.1)
-- Dumped by pg_dump version 13.5 (Ubuntu 13.5-0ubuntu0.21.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: availability; Type: TABLE; Schema: public; Owner: postgres
--

DROP TABLE public.application;
DROP TABLE public.applicant;
DROP TABLE public.recruiter;
DROP TABLE public.availability;
DROP TABLE public.competence;
DROP TABLE public.competence_profile;


CREATE TABLE public.availability (
    availability_id integer NOT NULL,
    applicant_id integer, 
    from_date date,
    to_date date
);


ALTER TABLE public.availability OWNER TO kth2019repping;

--
-- Name: availability_availability_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.availability ALTER COLUMN availability_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.availability_availability_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: competence; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.competence (
    competence_id integer NOT NULL,
    name character varying(255),
    language character varying(10) NOT NULL,
    definition_id int NOT NULL
);


ALTER TABLE public.competence OWNER TO kth2019repping;

--
-- Name: competence_competence_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.competence ALTER COLUMN competence_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.competence_competence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: competence_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.competence_profile (
    competence_profile_id integer NOT NULL,
    applicant_id integer,
    competence_id integer,
    years_of_experience numeric(4,2)
);


ALTER TABLE public.competence_profile OWNER TO kth2019repping;

--
-- Name: competence_profile_competence_profile_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.competence_profile ALTER COLUMN competence_profile_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.competence_profile_competence_profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.applicant (
    applicant_id integer NOT NULL,
    name character varying(255),
    surname character varying(255),
    pnr character varying(255),
    email character varying(255),
    password character varying(255)
);


ALTER TABLE public.applicant OWNER TO kth2019repping;

CREATE TABLE public.recruiter (
    recruiter_id integer NOT NULL,
    name character varying(255),
    surname character varying(255),
    password character varying(255),
    username character varying(255)
);

ALTER TABLE public.recruiter OWNER TO kth2019repping;

ALTER TABLE public.applicant ALTER COLUMN applicant_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.applicant_applicant_id_seq
    START WITH 11
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: person_person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.recruiter ALTER COLUMN recruiter_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.recruiter_recruiter_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.application (
    application_id integer NOT NULL,
    applicant_id integer NOT NULL,
    accepted boolean,
    recruiter_id integer,
    version integer NOT NULL default 0
);

ALTER TABLE public.application OWNER TO kth2019repping;

ALTER TABLE public.application ALTER COLUMN application_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.application_application_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
