### Defect Log
| Time   | Description |
| :----: | ------------|
| 7/29   | 没有注意到使用 `try-with-resource statement`时会自动关闭 `BufferedReader`，而 `Reader` 关闭后，会自动关闭里面的 `stream`，由此再想从 `System.in` 中输入时，就会抛出 `Stream closed` 异常。
| 7/30   | `Gson` 中不支持直接对 `{}`中的东西使用方法除了 `getAsJsonObject` 之外的方法，今天踩了这个坑 |
| 8/2    | `getEquipment()` 方法中未使用 `Optional` 进行检测，导致存在抛出 `NullPointerException` 的风险 |
| 8/3    | 在 `ConsumableRepository` 的 `getConsumable()` 方法中没有注意到输入的名称必须是小写。 |
| 8/3    | 在使用 `GsonBuilder` 创建 `Gson` 对象时，忘记使用 `create()` 方法 |
| 8/4    | 从控制台获取用户输入的数字用于选择数组的元素时没有进行越界检查 |
| 8/4    | 没有注意到大小写的转换 |
| 8/7    | 在 `Storage` 类的 `queryNormalConsumable()` 方法中当 `consumableBag` 中的物品减少时，没有对由它的 `keySet` 组成的 `List` 进行相应的同步处理与检查 |
| 8/8    | `json` 文件中，在最后一项的末尾加了逗号导致解析抛出异常 |
| 8/8    | 在向 `Storage` 中通过 `addConsumables()` 添加物品时，没有考虑物品数量是零或负数的情况 |

### Code Review Checklist
| Num    | Description |
| :----: | ------------|
| 1      | 检查所有打开的流是否提早关闭（尤其是 `System.in` ）或是忘记关闭 |
| 2      | 检查对于返回值是 `null` 的方法是否有进行检测 |
| 3      | 进行字符串的匹配时检查是否需要进行大小写的转换 |
| 4      | 从控制台获取的数字作用于数组时，检查是否存在越界的可能 |
| 5      | 当一个容器类的内容来自于另外一个容器类时，检查他们是否需要进行同步 |
| 6      | 检查 `json` 文件的末尾是否有多余的逗号 |
| 7      | 若某对象存在添加指定数量的其他对象的方法时，考虑输入数量是0或负数的情况 |
| Last   | 检查控制流程是否正确 |

### Design Log
| Time   | Description |
| :----: | ------------|
| 8/2    | `Player` 类集成了过多职责，应该创建个 `EquipmentHelper` 之类的辅助类来分管各项功能，而不是将各种细小功能集成在 `Player` 类中，在 `Storage` 的设计时将会尝试改进 |
| 8/3    | 突然想到 `Consumable` 等一系列的类可以使用装饰器模式 |
| 8/4    | 意识到在对 `Storage` 类的设计中，应该为不同的背包单独创建一个类，在每个类中实现查看该背包的方法 |