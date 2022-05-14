## 留忆 : IdeaList Part

### 未完成

- [ ] 添加返回主界面的按按钮
- [ ] 为 ToolBar 提供背景图


### 功能

- 展示当日感悟
    - 按照创建感悟的时间展示当日的感悟、心得
    - 支持显示文字、图像
- 添加感悟功能
    - 支持添加文字、上传图片

### UI 部分

#### 顶部 ToolBar

- 采用 `CoordinatorLayout`  + `AppBarLayout` + `RecyclerView`实现顶部条下拉操作、感悟界面的滚动试图



#### 文本、图片视图

- 采用 `TextView`、`ImageView`，结合`LinearLayout`进行布局



### Activity | Layout

| Activity Name          | Layout                                     | 描述                                     |
| ---------------------- | ------------------------------------------ | ---------------------------------------- |
| `IdeaActivity`      | `activity_scrolling.xml`                    | 展示当日感悟        |
| | `content_scrolling.xml` | 感悟滚动视图 |
| | `idea_item_view.xml`| 滚动视图子项布局 |
| `IdeaItemCreationActivity` | `activitiy_idea_item_creation.xml`              | 发表当日感悟                    |





### DBHelper

- 数据库中表名为  `idea`



#### API

##### `getIdeaItemListByDater`

- Description : 获取某一天全部感悟

- Parameter :  `String`

  > 格式为 `yyyy-mm-dd`

- ReturnValue : `MutableList<IdeaItem>`



### Idea 数据类

#### IdeaElement

| 成员变量      | 数据类型                                          |
| ---------- | ------------------------------------- |
| idea_date  | String : 感悟创建日期                          |
| idea_text  | String : 感悟文本内容                              |
| idea_image | String : 感悟图片资源                                 |
| idea_tag   | String : 感悟时间标识符 |



### Adapter

- `IdeaViewAdapter`













