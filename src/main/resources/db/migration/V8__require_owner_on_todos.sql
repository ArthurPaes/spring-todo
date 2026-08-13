UPDATE todos
   SET user_id = (SELECT id FROM users ORDER BY id LIMIT 1)
 WHERE user_id IS NULL;

DELETE FROM todos WHERE user_id IS NULL;

ALTER TABLE todos ALTER COLUMN user_id SET NOT NULL;
