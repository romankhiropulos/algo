WITH RECURSIVE clinic_temp(id, position, parent_id, path, level) AS (SELECT id, parent_id, position, ' | ' || position, 0
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
ORDER BY level