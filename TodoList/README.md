## 留忆 : TodoList Part

### 未完成

- [ ] 为每个卡片提供背景图片
- [x] 为 ToolBar 提供背景图
- [x] 调整布局，文字大小，margin 等
- [x] 添加 checkBox 点击事件
- [x] 整体接入调试




### 功能

- 展示当日全部任务
    - 按照创建事件和完成情况排序显示
    - 已完成任务和未完成任务卡片对应不同背景
- 单击未完成任务进入任务执行页面
    - 提供计时功能，实时统计当前任务专注总时间
- 单击已完成任务进入任务查看页面
- 长按未完成任务进入任务编辑页面
- 主页添加按钮进入添加任务界面



### UI 部分

#### 顶部 ToolBar

- 采用 `CoordinatorLayout`  + `AppBarLayout` 实现顶部条下拉操作以及顶部 返回 / 保存 按钮



#### 主页缩略卡片

- recyclerView + cardView + imageButton + checkbox, 里面是多个 linearLayout 嵌套
- 





#### 文本框悬浮效果

- 采用 `cardView`



### Activity | Layout

| Activity Name          | Layout                                     | 描述                                     |
| ---------------------- | ------------------------------------------ | ---------------------------------------- |
| `AllTaskActivity`      | `activity_all_task.xml`                    | 展示当日全部任务 （包括任务详情）        |
| `DoneTaskShowActivity` | `activity_done_task_show.xml`              | 展示某已完成任务详情                     |
| `TodoTaskEditActivity` | `activity_todo_task_edit.xml`              | 添加新任务 / 编辑未完成任务              |
| `TaskStartAcitivy`     | `activity_task_start.xml`                  | 启动某未完成任务 （可计时）              |
|                        | `activity_single_task_entry.xml`           | 全部任务展示页面中 recyclerView 的子条目 |
|                        | `activity_tumbnail_recyclerview_entry.xml` | 主页面缩略图中 recyclerView  的子条目    |





### DBHelper

- 数据库中表名为  `task`



#### API

##### `queryTasksInOneDay`

- Description : 获取某一天全部任务

- Parameter :  `String`

  > 格式为 `yyyy-mm-dd`

- ReturnValue : `MutableList<TaskElement>`



##### `queryTaskInfo`

- Decription : 获取某一任务详情

- Parameter : `String`

  > 需传入任务的标签` task_tag`

- ReturnValue : `MutableMap<String?, String?>`

  > 可根据 `task_exist` 查询任务是否存在

  ```
  Key 				Value
  task_exist			"True" | "False"
  task_title  
  task_detail
  task_duration
  task_tag
  task_date
  task_status
  ```





### Task 数据类

#### TaskElement

| 成员变量      | 数据类型                                          |
| ------------- | ------------------------------------------------- |
| task_title    | String : 任务主题                                 |
| task_detail   | String : 任务详情                                 |
| task_tag      | String : 任务唯一标识符 （`yyyy-MM-dd-HH-mm-ss` ) |
| task_status   | String : 任务完成状态 (`Done` | `Todo`)           |
| task_duration | String : 任务专注事件 (以总秒数记)                |
| task_date     | String : 任务创建日期 (`yyyy-MM-dd`)              |





### Adapter

- `AllTaskAdapter`
- `TaskThumbnailAdapter`





- windowsBackground



UI 布局 | 图片背景 

recyclerView 返回时不刷新



runOnUiThread : 将 runnable 对象返回到主线程执行



**CoordinatorLayout**是在Support 包中功能强大的布局容器，它本质是一个FrameLayout，然而它允许开发者通过自定义Behavior协调各个子view,实现各种复杂酷炫的UI交互效



Chronometer

