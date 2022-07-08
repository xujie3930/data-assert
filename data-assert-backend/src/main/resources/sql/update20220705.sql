
INSERT INTO tag_category(id, pid, `level`, `name`, `describe`, del_flag, create_time, create_by, create_user_id, update_time, update_by, update_user_id) values (1,0, 1, '默认标签分类', '默认标签分类', 0, now(), 'admin', '0', now(), 'admin', '0');


INSERT INTO tag_category_relation(id, tag_category_id, tag_id, del_flag, create_time) SELECT id, 1, id, 0, now() FROM tag WHERE del_flag=0;