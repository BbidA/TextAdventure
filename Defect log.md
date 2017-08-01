| time   | description |
| :----: | ------------|
| 7/29   | 没有注意到使用 `try-with-resource statement`时会自动关闭 `BufferedReader`，而 `Reader` 关闭后，会自动关闭里面的 `stream`，由此再想从 `System.in` 中输入时，就会抛出 `Stream closed` 异常。
| 7/30   | `Gson` 中不支持直接对 `{}`中的东西使用方法除了 `getAsJsonObject` 之外的方法，今天踩了这个坑 |