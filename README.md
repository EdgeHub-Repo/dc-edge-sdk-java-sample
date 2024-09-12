# EdgeSync360 EdgeHub Java SDK Example

This repository provides an example of how to use the EdgeSync360.EdgeHub.Java.SDK to connect an edge device to the EdgeHub, configure devices, upload data, and send real-time telemetry using different types of tags.

# Prerequisites

JDK 8 or later installed on your machine.
A valid API Key and Node ID from the EdgeSync360 EdgeHub platform.

# Installation

1. Clone this repository:

```bash
git clone https://github.com/your-repository/edgesync360-sdk-example.git
cd edgesync360-sdk-example
```

2. Install the required SDK by adding the EdgeSync360.EdgeHub.Java.SDK dependency to your project.

If you are using Gradle, add the following to your build.gradle file:

```bash
dependencies {
    implementation 'io.github.edgehub-repo:EdgeSync360.EdgeHub.Edge.Java.SDK:1.0.1'
}
```

If you are using Maven, add the following to your pom.xml:

```
<dependency>
    <groupId>io.github.edgehub-repo</groupId>
    <artifactId>EdgeSync360.EdgeHub.Edge.Java.SDK</artifactId>
    <version>1.0.1</version>
</dependency>
```

# Usage

1. Set Up the Configuration

In Main.java, the EdgeAgentOptions class is used to configure the connection settings. You will need to replace the placeholders with your actual configuration values.

```csharp
EdgeAgentOptions options = new EdgeAgentOptions();
 options.ConnectType = ConnectType.DCCS; // DCCS is the default connection type.
 options.DCCS = new DCCSOptions("YOUR_CREDENTIAL_KEY", "YOUR_API_URL"); // Replace with your actual DCCS Credential Key and API URL.
 options.MQTT = new MQTTOptions("127.0.0.1", 1883, "admin", "admin", Protocol.TCP); // Replace with your MQTT settings if using MQTT.
 options.NodeId = "YOUR_NODE_ID"; // Replace with your Node ID
 options.Type = EdgeType.Gateway; // Gateway or Device, default is Gateway
 options.DeviceId = "SmartDevice1"; // Device ID if type is set to Device

```

- CredentialKey: Your DCCS API credential key.
- APIUrl: The URL for the DCCS service.
- NodeId: The node identifier from the portal.

2. Run the Program

   This code connects to the EdgeHub, uploads device configurations, and sends telemetry data in real-time.

   You can run the program by executing the following command:

   ```bash
   ./gradlew run
   ```

   The program will:

   1. Connect to the EdgeHub.
   2. Upload a configuration that defines devices, blocks, and tags.
   3. Periodically upload simulated data (analog, discrete, and text tag data) every second for 10 iterations.

3. Code Overview

   - EdgeAgentOptions: Defines the connection and edge configuration.
   - DCCSOptions: Holds DCCS-specific settings like the CredentialKey and APIUrl.
   - MQTTOptions: Holds MQTT-specific settings like HostName, Port, and credentials.
   - EdgeAgent: The main agent responsible for connecting and uploading data to the EdgeHub.
   - EdgeConfig: Contains the configuration for devices and tags (analog, discrete, text).
   - EdgeData: Represents the telemetry data being sent.

4. Customize the Configuration
   In Main.java, you can customize the devices and tags by modifying the following sections:

   - AnalogTagConfig: Configuration for an analog sensor tag.
   - DiscreteTagConfig: Configuration for a discrete tag with binary states.
   - TextTagConfig: Configuration for a text-based tag.

   Example for adding a new tag:

   ```csharp
    EdgeConfig.AnalogTagConfig newAnalogTag = new EdgeConfig.AnalogTagConfig();
    newAnalogTag.Name = "NewAnalogTag";
    newAnalogTag.Description = "New Analog Tag Description";
    newAnalogTag.SpanHigh = 100;
    newAnalogTag.SpanLow = 0;
    newAnalogTag.EngineerUnit = "Celsius";
    blockConfig.AnalogTagList.add(newAnalogTag);

   ```

5. Uploading Data
   Simulated data is uploaded using the EdgeAgent.SendData() method. In the example, the telemetry data is generated randomly and sent every second.

   ```csharp
    for (int i = 0; i < 10; i++) {
        EdgeData data = new EdgeData();
        data.setTimestamp(new Date());
        // Add analog, discrete, and text tag data here
        edgeAgent.SendData(data);
    }
   ```

6. Updated Connected Method with Configuration Choice
   To add the functionality where you can choose to upload either a general or block-specific configuration and data, you can introduce a new parameter or flag in your code that allows the user to make this selection.

   Here's an updated version of your Connected method with added functionality:

   ```csharp
    public void Connected(EdgeAgent agent, EdgeAgentConnectedEventArgs args, boolean isBlockConfig) {
        System.out.println("Connected");

        // Upload configuration based on the user's choice.
        if (isBlockConfig) {
            // Upload block-specific configuration.
            doUploadBlockCfg(agent);
        } else {
            // Upload general/common configuration.
            doUploadCfg(agent);
        }

        // Initialize EdgeHelpers.
        EdgeHelpers edgeHelpers = new EdgeHelpers(agent);

        // Configure data sending based on the user's choice.
        if (isBlockConfig) {
            // To enable block-specific data uploading, configure block names and set
            // isBlockData to true.
            edgeHelpers.blockNames = new String[] { "Pump01", "Pump02" };
            edgeHelpers.isBlockData = true;
        } else {
            // Set block data to false for general data upload.
            edgeHelpers.isBlockData = false;
        }

        // Configure the data sending loop interval (in milliseconds).
        // The default interval is 1000 milliseconds (1 second).
        edgeHelpers.sendLoopInterval = 10000;

        // Start sending data.
        doSendDataLoop(edgeHelpers);
    }

   ```

   Explanation of Changes:

   - New Parameter (isBlockConfig): A boolean parameter named isBlockConfig is added to the `Connected` method. It allows the user to specify whether to upload block-specific configuration or general configuration.
   - Conditional Configuration Upload:
     - If `isBlockConfig` is `true`, `doUploadBlockCfg(agent)` is called to upload the block-specific configuration.
     - If `isBlockConfig` is `false`, `doUploadCfg(agent)` is called to upload the general/common configuration.
   - EdgeHelpers Configuration:
     - If `isBlockConfig` is `true`, `edgeHelpers.isBlockData` is set to `true`, and block names are configured.
     - If `isBlockConfig` is `false`, `edgeHelpers.isBlockData` is set to `false`, indicating that general data should be uploaded.

# Conclusion

This example demonstrates how to use the EdgeSync360.EdgeHub.Java.SDK to connect, configure devices, and send telemetry data to EdgeHub. Customize the tags and device configurations as needed for your specific use case.
