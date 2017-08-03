### Defect Log
| time   | description |
| :----: | ------------|
| 7/29   | 没有注意到使用 `try-with-resource statement`时会自动关闭 `BufferedReader`，而 `Reader` 关闭后，会自动关闭里面的 `stream`，由此再想从 `System.in` 中输入时，就会抛出 `Stream closed` 异常。
| 7/30   | `Gson` 中不支持直接对 `{}`中的东西使用方法除了 `getAsJsonObject` 之外的方法，今天踩了这个坑 |
| 8/2    | `getEquipment()` 方法中未使用 `Optional` 进行检测，导致存在抛出 `NullPointerException` 的风险 |
| 8/3    | 在 `ConsumableRepository` 的 `getConsumable()` 方法中没有注意到输入的名称必须是小写。 |
| 8/3    | 在使用 `GsonBuilder` 创建 `Gson` 对象时，忘记使用 `create()` 方法 |

### Design Log
| time   | description |
| :----: | ------------|
| 8/2    | `Player` 类集成了过多职责，应该创建个 `EquipmentHelper` 之类的辅助类来分管各项功能，而不是将各种细小功能集成在 `Player` 类中，在 `Storage` 的设计时将会尝试改进 |
| 8/3    | 突然想到 `Consumable` 等一系列的类可以使用装饰器模式 |