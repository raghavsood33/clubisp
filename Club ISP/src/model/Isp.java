package model;

public class Isp {
	
	String name;
	String iface;
	String gateway;
	String subnet;
	String network;
	Boolean online;
	Boolean enabled;
	String ip_address;
	int weight;
	int mask;
	
	public int getMask() {
		return mask;
	}
	public void setMask(int mask) {
		this.mask = mask;
	}
	String protocol;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIface() {
		return iface;
	}
	public void setIface(String iface) {
		this.iface = iface;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getSubnet() {
		return subnet;
	}
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public Boolean getOnline() {
		return online;
	}
	public void setOnline(Boolean online) {
		this.online = online;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	@Override
	public String toString() {
		return "Isp [name=" + name + ", iface=" + iface + ", gateway="
				+ gateway + ", subnet=" + subnet + ", network=" + network
				+ ", online=" + online + ", enabled=" + enabled
				+ ", ip_address=" + ip_address + ", weight=" + weight
				+ ", mask=" + mask + ", protocol=" + protocol + "]";
	}
	
}

