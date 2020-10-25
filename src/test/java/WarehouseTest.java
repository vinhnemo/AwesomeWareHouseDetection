import com.detection.detector.DetectorImpl;
import com.detection.detector.ITakeDeliverySolution;
import com.detection.utils.Broker;
import com.detection.utils.CandidateWareHouse;
import com.detection.utils.DeliverySolution;
import com.detection.utils.MsgResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author vinhnp
 * @create 25/10/2020
 */
public class WarehouseTest {


    @Test
    public void test01SingleWarehouse() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("book", 2);
        hashMap.put("pen", 3);
        Broker customer = new Broker("hn", hashMap, null);

        ITakeDeliverySolution takeDeliverySolution = new DetectorImpl();

        HashMap<String, Integer> hashMap1 = new HashMap<String, Integer>();
        hashMap1.put("book", 1);
        Broker w1 = new Broker("hn", hashMap1, "hn1");

        HashMap<String, Integer> hashMap11 = new HashMap<String, Integer>();
        hashMap11.put("book", 3);
        hashMap11.put("pen", 3);
        Broker w13 = new Broker("hn", hashMap11, "hn13");


        HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
        hashMap2.put("book", 10);
        hashMap2.put("pen", 7);
        Broker w2 = new Broker("hn", hashMap2, "hn10");

        HashMap<String, Integer> hashMap3 = new HashMap<String, Integer>();
        hashMap3.put("book", 30);
        hashMap3.put("pen", 70);
        Broker w3 = new Broker("hcm", hashMap3, "hcm30");

        List<Broker> brokerList = new LinkedList<>();
        brokerList.add(w1);
        brokerList.add(w2);
        brokerList.add(w3);
        brokerList.add(w13);

        MsgResult deliverySolution = takeDeliverySolution.detectDeliverySolution(customer, brokerList);

        System.out.println("init test case...");
        System.out.println(customer.toString());
        brokerList.forEach(broker -> {
            System.out.println(broker.toString());
        });

        Assert.assertEquals(1, deliverySolution.getWarehouseCount());
        Assert.assertEquals(17, deliverySolution.getProductMatching());
        LinkedList<DeliverySolution> deliverySolutions = deliverySolution.getDeliverySolutionList();
        Assert.assertTrue(deliverySolutions.size() == 1);


        CandidateWareHouse candidateWareHouse = deliverySolutions.getLast().getCandidateWareHouseList().get(0);

        Broker warehouse = candidateWareHouse.getWareHouse();
        Assert.assertEquals("hn10", warehouse.getName());
        Assert.assertTrue(candidateWareHouse.getProductTaking().get("book") == 2);
        Assert.assertTrue(candidateWareHouse.getProductTaking().get("pen") == 3);


    }

    @Test
    public void test01MultiWarehouse() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("book", 2);
        hashMap.put("pen", 3);
        Broker customer = new Broker("hn", hashMap, null);

        ITakeDeliverySolution takeDeliverySolution = new DetectorImpl();

        HashMap<String, Integer> hashMap1 = new HashMap<String, Integer>();
        hashMap1.put("book", 1);
        Broker w1 = new Broker("hn", hashMap1, "hn1");

        HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
        hashMap2.put("book", 10);
        Broker w2 = new Broker("hcm", hashMap2, "hcm10");

        HashMap<String, Integer> hashMap3 = new HashMap<String, Integer>();
        hashMap3.put("pen", 5);
        Broker w3 = new Broker("hcm", hashMap3, "hcm5");

        List<Broker> brokerList = new LinkedList<>();
        brokerList.add(w1);
        brokerList.add(w2);
        brokerList.add(w3);

        MsgResult deliverySolution = takeDeliverySolution.detectDeliverySolution(customer, brokerList);

        System.out.println("init test case...");
        System.out.println(customer.toString());
        brokerList.forEach(broker -> {
            System.out.println(broker.toString());
        });

        Assert.assertEquals(3, deliverySolution.getWarehouseCount());
        Assert.assertEquals(16, deliverySolution.getProductMatching());
        LinkedList<DeliverySolution> deliverySolutions = deliverySolution.getDeliverySolutionList();
        for (DeliverySolution solution : deliverySolutions) {


            CandidateWareHouse candidateWareHouse = solution.getCandidateWareHouseList().get(0);
            Broker warehouse = candidateWareHouse.getWareHouse();
            switch (warehouse.getName()) {
                case "hn1": {
                    Assert.assertTrue(candidateWareHouse.getProductTaking().get("book") == 1);
                    break;
                }
                case "hcm10": {
                    Assert.assertTrue(candidateWareHouse.getProductTaking().get("book") == 1);
                    break;
                }
                case "hcm5": {
                    Assert.assertTrue(candidateWareHouse.getProductTaking().get("pen") == 3);
                    break;
                }

            }
        }
    }

    @Test
    public void test01SingleWarehouseWithRemainProductCount() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("book", 2);
        hashMap.put("pen", 3);
        Broker customer = new Broker("hn", hashMap, null);

        ITakeDeliverySolution takeDeliverySolution = new DetectorImpl();

        HashMap<String, Integer> hashMap1 = new HashMap<String, Integer>();
        hashMap1.put("book", 1);
        Broker w1 = new Broker("hn", hashMap1, "hn1");

        HashMap<String, Integer> hashMap11 = new HashMap<String, Integer>();
        hashMap11.put("book", 10);
        hashMap11.put("pen", 7);
        hashMap11.put("others", 1000);
        Broker w13 = new Broker("hn", hashMap11, "hn13");


        HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
        hashMap2.put("book", 10);
        hashMap2.put("pen", 7);
        Broker w2 = new Broker("hn", hashMap2, "hn10");

        HashMap<String, Integer> hashMap3 = new HashMap<String, Integer>();
        hashMap3.put("book", 30);
        hashMap3.put("pen", 7);
        Broker w3 = new Broker("hcm", hashMap3, "hcm30");

        List<Broker> brokerList = new LinkedList<>();
        brokerList.add(w1);
        brokerList.add(w2);
        brokerList.add(w3);
        brokerList.add(w13);

        MsgResult deliverySolution = takeDeliverySolution.detectDeliverySolution(customer, brokerList);

        System.out.println("init test case...");
        System.out.println(customer.toString());
        brokerList.forEach(broker -> {
            System.out.println(broker.toString());
        });

        Assert.assertEquals(1, deliverySolution.getWarehouseCount());
        Assert.assertEquals(17, deliverySolution.getProductMatching());
        LinkedList<DeliverySolution> deliverySolutions = deliverySolution.getDeliverySolutionList();
        Assert.assertTrue(deliverySolutions.size() == 1);


        CandidateWareHouse candidateWareHouse = deliverySolutions.getLast().getCandidateWareHouseList().get(0);
        Broker warehouse = candidateWareHouse.getWareHouse();
        Assert.assertEquals("hn13", warehouse.getName());
        Assert.assertTrue(candidateWareHouse.getProductTaking().get("book") == 2);
        Assert.assertTrue(candidateWareHouse.getProductTaking().get("pen") == 3);


    }
}
