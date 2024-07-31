# MIot: Doc & Api

## 设备列表

> https://home.miot-spec.com/

## 设备通信 (miotspec)

> https://iot.mi.com/new/doc/accesses/direct-access/extension-development/basic-functions/communication

### spec 参数解释

> https://iot.mi.com/v2/new/doc/introduction/knowledge/explanation

#### did

设备 ID(Device ID)是小米 IoT 开发者平台分配的，用于唯一识别某一个具体设备的唯一标识。使用小米智能模组的设备，模组中自带 DID。使用 Linux/Andorid SDK 开发的产品，DID 包含在申请的三元组中。

#### siid

服务 ID(service-iid)，MIOT-Spec 协议中某项服务的唯一标识。服务(Service)指某一类互相有联系的功能合集，其内部包含各种属性(Property)、事件(Event)和方法(Action)。

在计算机程序中，同一类属性和方法等往往会放进类(Class)中。Spec 协议中的服务也像 Class 一样，如灯品类的 light 服务中，包含了开关、亮度调节、色温调节等功能。

#### piid

属性 ID(property-iid)，MIOT-Spec 协议中某项服务中属性的唯一标识。单一的功能操作可以定义为属性，属性支持被查询、控制、订阅。例如灯的开关、亮度调节、色温调节。

#### aiid

方法 ID(action-iid)，MIOT-Spec 协议中方法的唯一标识，主要用于向设备下发控制指令。方法可支持同时下发多个属性（以属性作为输入、输出参数），如电饭煲开始煮饭。

#### eiid

事件 ID(event-iid)，MIOT-Spec 协议中事件的唯一标识，主要用于设备向云端上报组合数据。事件可支持上报多个属性（以属性作为输出参数），如门锁被打开。

#### RPC

RPC 是远程过程调用(Remote Procedure Call)的缩写形式。接入米家的设备，其远程云端控制以及小爱语控，自动化场景等功能的实现，依赖云与设备之间的数据通信，也属于一种 RPC，数据传输协议使用的是 MIOT-SPEC。

例如下面的示例就是一条完整的符合 MIOT-SPEC 协议的 RPC 指令。

```json
{
    "from": "4",
    "id": 39950136,
    "method": "set_properties",
    "params": [
        {
            "did": "462033xxx",
            "piid": 1,
            "siid": 2,
            "value": true
        }
    ]
}
```

#### iid

即实例 ID，为了区分不同的实例，引入一个概念：iid（Instance ID，实例 ID）  
iid 用整型表示，一个 iid 在同一级是唯一的，如：  
1. 在一个 Device 中，每个 Service 的 iid 是唯一的。  
2. 在一个 Service 的 properties 中，Property 的 iid 是唯一的。  
3. 在一个 Service 的 actions 中，Action 的 iid 是唯一的。  
4. 在一个 Service 的 events 中，Event 的 iid 是唯一的。  

## 标准功能定义

> https://iot.mi.com/new/doc/tools-and-resources/design/spec/xiaoai

## 读取标准定义

> https://iot.mi.com/new/doc/tools-and-resources/design/spec/overall

小米IoT 设备协议规范定义了标准的设备、属性、方法、事件和服务，为了帮助开发者快速定义产品的功能，开发者可从www.miot-spec.org 上使用标准HTTP/GET 请求读取并参考规范定义的标准内容，定义待接入产品的功能定义。

### 读取Type 列表

读取所有的DeviceType：
http://miot-spec.org/miot-spec-v2/spec/devices

读取所有的ServiceType：
http://miot-spec.org/miot-spec-v2/spec/services

读取所有的PropertyType：
http://miot-spec.org/miot-spec-v2/spec/properties

读取所有的ActionType：
http://miot-spec.org/miot-spec-v2/spec/actions

读取所有的EventType：
http://miot-spec.org/miot-spec-v2/spec/events

### 读取具体Type 定义

读取一个DeviceType的具体定义（如灯）：  
http://miot-spec.org/miot-spec-v2/spec/device?type=urn:miot-spec-v2:device:light:0000A001  

读取一个ServiceType的具体定义（如风扇）：  
http://miot-spec.org/miot-spec-v2/spec/service?type=urn:miot-spec-v2:service:fan:00007808  

读取一个PropertyType的具体定义（如开关）：  
http://miot-spec.org/miot-spec-v2/spec/property?type=urn:miot-spec-v2:property:on:00000006  

读取一个ActionType的具体定义（如运行）：  
http://miot-spec.org/miot-spec-v2/spec/action?type=urn:miot-spec-v2:action:play:0000280B  

### 读取实例定义

设备实例是开发者遵循小米IoT 设备协议规范所定义和创建的设备实例，开发者可从www.miot-spec.org 上使用标准HTTP/GET 读取实例定义，为了方便开发者快速读取待接入产品的功能定义，开发者可在功能定义界面使用“查看Json” 功能。  

读取所有设备实例列表：  
http://miot-spec.org/miot-spec-v2/instances  

读取某个实例的详细定义：  
http://miot-spec.org/miot-spec-v2/instance?type=urn:miot-spec-v2:device:outlet:0000A002:xxxxx （xxxx 为vendor-product）