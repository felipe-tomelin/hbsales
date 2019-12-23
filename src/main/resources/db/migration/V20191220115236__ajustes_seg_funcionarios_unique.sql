ALTER TABLE seg_funcionarios ADD CONSTRAINT unique_email UNIQUE (email);
ALTER TABLE seg_funcionarios ADD CONSTRAINT unique_uuid UNIQUE (uuid);