package bld;

import java.util.List;


public class SRuleMap {
	
	public SRuleMap(String rule, List<String> keys) {
		this.rule = rule;
		this.keys = keys;
	}
	
	
	private String rule;
	private List <String> keys;
	
	
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	

}
