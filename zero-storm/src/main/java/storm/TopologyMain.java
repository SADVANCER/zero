package storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import storm.bolt.WordCounter;
import storm.bolt.WordNormalizer;
import storm.spout.WordReader;

/**
 * @author ningcheng
 * @date 2017/10/10
 */
public class TopologyMain {


    public static void main(String[] args) throws InterruptedException{
        //定义拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());
        builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");
        builder.setBolt("word-counter", new WordCounter(),2).fieldsGrouping("word-normalizer", new Fields("word"));

        //配置
        Config conf = new Config();
        conf.put("wordsFile", args[0]);
        conf.setDebug(false);

        //运行拓扑
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topologie", conf, builder.createTopology());
        Thread.sleep(1000);
        cluster.shutdown();
    }
}
