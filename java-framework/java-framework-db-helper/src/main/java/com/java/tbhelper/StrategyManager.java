package com.java.tbhelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StrategyManager {

    private Logger logger = LoggerFactory.getLogger(StrategyManager.class);
    private Map<String, Strategy> strategies = new ConcurrentHashMap<String, Strategy>();

    public Strategy getStrategy(String key) {
        return strategies.get(key);
    }

    public Map<String, Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(Map<String, Strategy> strategies) {
        try {
            if(strategies == null || strategies.isEmpty()){
                logger.error("init strategty error");
                System.exit(0);
            }
            this.strategies = strategies;
        } catch (Exception e) {
            logger.error("init strategty error", e);
            System.exit(0);
        }
        printDebugInfo();
    }

    private void printDebugInfo() {
        StringBuffer msg = new StringBuffer("init " + strategies.size() + " strategty");
        for (String key : strategies.keySet()) {
            msg.append("\n");
            msg.append(key);
            msg.append("  --->  ");
            msg.append(strategies.get(key));
        }
        logger.debug(msg.toString());
    }
}
