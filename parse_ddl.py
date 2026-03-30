import glob
import os
import re
import xml.etree.ElementTree as ET

mapper_files = glob.glob('dao/src/main/resources/mapper/**/*.xml', recursive=True)

jdbc_mysql_map = {
    'VARCHAR': 'VARCHAR(255)',
    'CHAR': 'CHAR(255)',
    'INTEGER': 'INT',
    'BIGINT': 'BIGINT',
    'TINYINT': 'TINYINT',
    'BIT': 'TINYINT(1)',
    'TIMESTAMP': 'DATETIME',
    'DATE': 'DATE',
    'TIME': 'TIME',
    'DECIMAL': 'DECIMAL(10,2)',
    'DOUBLE': 'DOUBLE',
    'REAL': 'FLOAT',
    'LONGVARCHAR': 'TEXT',
    'LONGVARBINARY': 'BLOB'
}

with open('schema.sql', 'w', encoding='utf-8') as out_f:
    out_f.write("-- Database DDL Generated from MyBatis Mapper XMLs\n\n")

    for filepath in mapper_files:
        if "KsRecVideoMapper" in filepath:
            continue

        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()

        table_name = "unknown_table"
        match = re.search(r'insert into\s+([a-zA-Z0-9_]+)', content, re.IGNORECASE)
        if match:
            table_name = match.group(1)

        content = re.sub(r'<!DOCTYPE.*?>', '', content, flags=re.DOTALL)
        try:
            root = ET.fromstring(content)
        except Exception as e:
            continue

        result_maps = root.findall('.//resultMap[@id="BaseResultMap"]')
        if not result_maps:
            continue

        rm = result_maps[0]

        out_f.write(f"-- Table mapper: {os.path.basename(filepath)}\n")
        out_f.write(f"CREATE TABLE `{table_name}` (\n")

        columns = []
        pks = []
        for child in rm:
            if child.tag in ['id', 'result']:
                col = child.get('column')
                jdbc_type = child.get('jdbcType', 'VARCHAR')
                sql_type = jdbc_mysql_map.get(jdbc_type.upper(), 'VARCHAR(255)')

                line = f"  `{col}` {sql_type}"
                if child.tag == 'id':
                    pks.append(col)
                    line += " NOT NULL"
                columns.append(line)

        if pks:
            columns.append(f"  PRIMARY KEY (`{'`, `'.join(pks)}`)")

        out_f.write(",\n".join(columns) + "\n")
        out_f.write(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;\n\n")
