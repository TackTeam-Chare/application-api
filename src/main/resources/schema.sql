IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'admin')
BEGIN
    CREATE TABLE dbo.admin (
        id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        role VARCHAR(20) NOT NULL DEFAULT 'ADMIN',
        created_at DATETIME2 DEFAULT GETDATE()
    );
END
