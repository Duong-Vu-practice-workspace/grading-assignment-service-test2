CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS assignments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_id UUID NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    published BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS test_scenarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    assignment_id UUID NOT NULL,
    sequence_order INT NOT NULL DEFAULT 0,
    name TEXT NOT NULL,
    http_method VARCHAR(10) NOT NULL,
    endpoint TEXT NOT NULL,
    query_params JSONB,
    request_body JSONB,
    expected_response_body JSONB,
    expected_status INT DEFAULT 200,
    weight INT DEFAULT 1,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS path_variables (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    scenario_id UUID NOT NULL,
    name TEXT NOT NULL,
    variable_order INT NOT NULL,
    type TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS docker_image_bases (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    image TEXT NOT NULL UNIQUE,
    platform TEXT NOT NULL,
    runtime_version TEXT,
    default_for_platform BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS assignment_docker_images (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    assignment_id UUID NOT NULL,
    docker_image_base_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_assignments_owner ON assignments(owner_id);
CREATE INDEX IF NOT EXISTS idx_scenarios_assignment ON test_scenarios(assignment_id);
CREATE INDEX IF NOT EXISTS idx_path_vars_scenario ON path_variables(scenario_id);

INSERT INTO docker_image_bases (name, image, platform, runtime_version, default_for_platform)
VALUES
    ('Python 3.11 Slim', 'python:3.11-slim', 'python', '3.11', true),
    ('OpenJDK 21 JRE', 'eclipse-temurin:21-jre', 'java', '21', true),
    ('Node.js 20 Slim', 'node:20-slim', 'node', '20', true),
    ('Golang 1.22 Alpine', 'golang:1.22-alpine', 'golang', '1.22', true)
ON CONFLICT (image) DO NOTHING;
