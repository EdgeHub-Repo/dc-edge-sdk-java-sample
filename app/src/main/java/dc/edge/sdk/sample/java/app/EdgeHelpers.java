package dc.edge.sdk.sample.java.app;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import EdgeSync360.EdgeHub.Java.SDK.EdgeAgent;
import EdgeSync360.EdgeHub.Java.SDK.model.edge.EdgeData;

public class EdgeHelpers {
    public DataLoopTask dataLoopTask;
    public Timer dataLoopTimer;
    public int sendLoopInterval;
    public boolean isBlockData;
    public String[] blockNames;

    public EdgeHelpers(EdgeAgent agent) {
        this.dataLoopTimer = new Timer();
        this.dataLoopTask = new EdgeHelpers.DataLoopTask(agent);
        this.sendLoopInterval = 1000;
    }

    class DataLoopTask extends TimerTask {
        EdgeAgent agent;

        DataLoopTask(EdgeAgent agent) {
            this.agent = agent;
        }

        public void run() {
            try {
                if (isBlockData) {
                    sendBlockDataOnce(agent);
                } else {
                    sendDataOnce(agent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDataOnce(EdgeAgent agent) {
        try {
            EdgeData data = prepareData();
            agent.SendData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EdgeData prepareData() {
        Random random = new Random();
        EdgeData data = new EdgeData();
        for (int i = 1; i <= 1; i++) {
            for (int j = 1; j <= 5; j++) {
                EdgeData.Tag aTag = new EdgeData.Tag();
                {
                    aTag.DeviceId = "Device" + i;
                    aTag.TagName = "ATag" + j;
                    aTag.Value = random.nextInt(100);
                }
                EdgeData.Tag dTag = new EdgeData.Tag();
                {
                    dTag.DeviceId = "Device" + i;
                    dTag.TagName = "DTag" + j;
                    dTag.Value = j % 2;
                }

                EdgeData.Tag tTag = new EdgeData.Tag();
                {
                    tTag.DeviceId = "Device" + i;
                    tTag.TagName = "TTag" + j;
                    tTag.Value = "TEST " + j;
                }

                data.TagList.add(aTag);
                data.TagList.add(dTag);
                data.TagList.add(tTag);
            }
        }
        data.Timestamp = new Date();
        return data;
    }

    public void sendBlockDataOnce(EdgeAgent agent) {
        try {
            EdgeData data = prepareBlockData();
            agent.SendData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EdgeData prepareBlockData() {
        Random random = new Random();
        EdgeData data = new EdgeData();

        for (int i = 1; i <= 1; i++) {
            for (String blockName : this.blockNames) {
                for (int j = 1; j <= 5; j++) {
                    EdgeData.Tag aTag = new EdgeData.Tag();
                    {
                        aTag.DeviceId = "Device" + i;
                        aTag.TagName = blockName + ":ATag" + j;
                        aTag.Value = random.nextInt(100);
                    }
                    EdgeData.Tag dTag = new EdgeData.Tag();
                    {
                        dTag.DeviceId = "Device" + i;
                        dTag.TagName = blockName + ":DTag" + j;
                        dTag.Value = j % 2;
                    }

                    EdgeData.Tag tTag = new EdgeData.Tag();
                    {
                        tTag.DeviceId = "Device" + i;
                        tTag.TagName = blockName + ":TTag" + j;
                        tTag.Value = "TEST " + j;
                    }

                    data.TagList.add(aTag);
                    data.TagList.add(dTag);
                    data.TagList.add(tTag);
                }
            }
        }
        data.Timestamp = new Date();
        return data;
    }
}
