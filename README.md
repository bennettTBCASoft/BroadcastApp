# Broadcast Receiver & 廣播App 

## 學習目標
- [ ]  了解 BroadcastReceiver 的用途及使用時機
- [ ]  使用 BroadcastReceiver 發送與接收廣播信息

## 介紹
> 當使用者操作手機時，手機電量不足，螢幕會顯示訊息提醒使用者裝置需要充電，或是當連結上Wifi時顯示相應的通知，這些系統層級的訊息都是使用『BroadcastReceiver』達成。

> BroadcastReceiver（廣播接收器）是應用程式元件之一，它用於**發送**和**接收**來自其他應用程式或系統的訊息，類似於訂閱與發佈的設計模式。
> BroadcastReceiver 分為 Broadcaster(廣播器)與Receiver（接收器）等兩種角色，Broadcaster可發送**自行定義**或**系統預設的廣播事件**，讓 Receiver 取得訊息，而 Receiver 則註冊特定的頻道，等待Broadcaster 發送訊息並執行對應的處理。

### Listener 與 BroadcastReceiver 比較

**Listener：**
- 必須綁定於View類型的對象
- 影響範圍被綁定對象所約束，例如：對按鈕設定Listener，但按鈕消失於螢幕時，Listener就無法攔截使用者的操作事件
- 僅接收單一對象的訊息
- 使用時機為單一元件對象且訊息為特定事件的情況

**BroadcastReceiver**
- 無需綁定特定對象，而是藉由**註冊**與**註銷**來決定是否接受訊息
- 影響範圍不受限，例如：註冊後若不註銷，就能不斷接收系統或自訂的訊息
- 可接受多數對象發送的訊息
- 使用時機為多數對象且訊息為系統事件或自訂事件的情況



| 差異比較 | Listener | BroadcastReceiver |
| -------- | -------- | -------- |
| 接收訊息類型     | 特定事件（點選、長按等）     | 系統事件或自訂事件（Intent）     |
| 發送對象及數量     | 對象明確且單一     | 對象叫不明確且多數     |
| 影響範圍     | 受限於綁定的元件對象     | 註冊後若不註銷就不受限     |
| 使用時機    | 單一元件對象且訊息為特定事件     | 多數對象且訊息為系統事件或自訂事件     |

## 建立 BroadcastReceiver
1. file -> new -> other -> BroadcastReceiver
2. 註冊於程式碼（這應該叫常用）

## 註冊與接收 BroadcastReceiver
- 使用 registerReceiver() 方法註冊
    `registerReceiver(receiver, IntentFilter("MyBroadcast"))`
    > Receiver 註冊時，需使用IntentFilter類別定義要接收的廣播事件，而Broadcast在發送事件前，也必須定義事件的「識別標籤」，識別標籤會決定要接收的事件類型，而廣播事件的類型分為「系統預設」、「自行定義」
- 使用 unregisterReceiver() 方法註銷 Receiver
    `unregisterReceiver(receiver)`
### 系統預設事件
> 常見廣播事件的識別標籤


| 識別標籤 | 說明 |
| -------- | -------- |
| ACTION_BATTERY_LOW     | 低電量通知     |
| ACTION_HEADSET_PLUG     | 耳機拔除或插入     |
| ACTION_SCREEN_ON     | 螢幕開啟     |
| ACTION_TIMEZONE_CHANGED     | 時區改變     |

- 示範系統預設（螢幕開啟通知）

![](https://i.imgur.com/6emRuzt.png)

![](https://i.imgur.com/U7rg5Y0.png)

- 示範自行定義

![](https://i.imgur.com/RLLJrOu.png)

![](https://i.imgur.com/yUaAFSU.png)

>由於是自行定義的廣播事件，因此需要由應用程式執行 **sendBroadcast()** 方法，來發送廣播讓 Receiver 接收，而 sendBroadcast() 方法通常會撰寫於其他程式區塊，例如：不同的Activity、Service或其他應用程式

```
val intent = Intent("MyBroadcast")
sendBroadcast(intent.putExtra("msg", "data"))
```

## 廣播App
- [ ] 建立三個Button供使用者切換收聽頻道
- [ ] 建立一個TextView顯示廣播的訊息
- [ ] 啟動Service並依據頻道發送廣播訊息
- [ ] 使用Thread延遲三秒後，發送頻道的播報訊息
- [ ] MainActivity的Receiver接收廣播訊息後，將其呈現於TextView

### 關鍵程式碼
1. 註冊廣播
    - `IntentFilter`
    - `registerReceiver`
2. 接收廣播
    - `建立object類別的 receiver 並繼承 BroadcastReceiver()`
    - `覆寫 onReceive(context, intent)`
3. 註銷廣播
    - `unregisterReceiver(receiver)` （通常寫在 **onDestroy**）
5. 發送廣播
    - `sendBroadcast(Intent(cahnnel).putExtra("msg", msg)`
7. 啟動 Service 並使用 Intent 傳遞使用者切換的頻道資訊
    - `val i = Intent(this, MyService::class.java)`
    - `startService(i.putExtra("channel", channel))`


###### tags: `kotlin` `Service` `Broadcast`
