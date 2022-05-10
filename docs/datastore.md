### liuyi.db

sqlite

table `task`

| task          |                   |
| ------------- | ----------------- |
| task_title    | string            |
| task_detail   | string            |
| task_tag      | string            |
| task_status   | string: todo/done |
| task_duration | string            |
| task_date     | string            |

table `idea`

| idea       |                                       |
| ---------- | ------------------------------------- |
| idea_date  | yyyy-mm-dd                            |
| idea_text  | content                               |
| idea_image | path                                  |
| idea_video | path                                  |
| idea_tag   | 精确到秒的创建时间yyyy-mm-dd-hh-mm-ss |

