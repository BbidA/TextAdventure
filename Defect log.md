### Defect Log
| time   | description |
| :----: | ------------|
| 7/29   | 没有注意到使用 `try-with-resource statement`时会自动关闭 `BufferedReader`，而 `Reader` 关闭后，会自动关闭里面的 `stream`，由此再想从 `System.in` 中输入时，就会抛出 `Stream closed` 异常。
| 7/30   | `Gson` 中不支持直接对 `{}`中的东西使用方法除了 `getAsJsonObject` 之外的方法，今天踩了这个坑 |
| 8/2    | `getEquipment()` 方法中未使用 `Optional` 进行检测，导致存在抛出 `NullPointerException` 的风险 |
| 8/3    | 在 `ConsumableRepository` 的 `getConsumable()` 方法中没有注意到输入的名称必须是小写。 |
| 8/3    | 在使用 `GsonBuilder` 创建 `Gson` 对象时，忘记使用 `create()` 方法 |
| 8/4    | 从控制台获取用户输入的数字用于选择数组的元素时没有进行越界检查 |
| 8/4    | 没有注意到大小写的转换 |

### Design Log
| time   | description |
| :----: | ------------|
| 8/2    | `Player` 类集成了过多职责，应该创建个 `EquipmentHelper` 之类的辅助类来分管各项功能，而不是将各种细小功能集成在 `Player` 类中，在 `Storage` 的设计时将会尝试改进 |
| 8/3    | 突然想到 `Consumable` 等一系列的类可以使用装饰器模式 |
| 8/4    | 意识到在对 `Storage` 类的设计中，应该为不同的背包单独创建一个类，在每个类中实现查看该背包的方法 |