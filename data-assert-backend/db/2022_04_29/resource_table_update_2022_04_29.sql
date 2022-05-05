-- 192.168.211.2
use daas;
update resource_table r set r.theme_id = (select a.parent_id from theme_resource a where a.id = r.resource_id);