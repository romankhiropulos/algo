-- Всё дерево, начиная от директора
WITH RECURSIVE clinic_temp(id, parent_id, position, path, level)
                   AS (SELECT id, parent_id, position, ' | ' || position, 0
                       FROM clinic_hierarchy
                       WHERE parent_id IS NULL
                       UNION ALL
                       SELECT c.id,
                              c.parent_id,
                              c.position,
                              cl.path || ' | ' || c.position,
                              cl.level + 1
                       FROM clinic_temp cl
                                JOIN clinic_hierarchy c ON cl.id = c.parent_id)
SELECT id, parent_id, position AS cat_name, path, level
FROM clinic_temp
ORDER BY level;

-- Ветвь врача ортопеда
WITH RECURSIVE clinic_temp(id, position, parent_id, path, level)
                   AS (SELECT id, parent_id, position, ' | ' || position, 0
                       FROM clinic_hierarchy
                       WHERE position = 'Врач ортопед'
                       UNION ALL
                       SELECT c.id,
                              c.parent_id,
                              c.position,
                              cl.path || ' | ' || c.position,
                              cl.level + 1
                       FROM clinic_temp cl
                                JOIN clinic_hierarchy c ON cl.id = c.parent_id)
SELECT id, parent_id, position AS cat_name, path, level
FROM clinic_temp
ORDER BY level;

-- Удаление ветви врача ортопеда
WITH RECURSIVE clinic_temp(id) AS (SELECT id
                                   FROM clinic_hierarchy
                                   WHERE position = 'Врач ортопед'
                                   UNION ALL
                                   SELECT c.id
                                   FROM clinic_temp cl
                                            JOIN clinic_hierarchy c ON cl.id = c.parent_id)
DELETE
FROM clinic_hierarchy
WHERE id IN (SELECT * FROM clinic_temp);