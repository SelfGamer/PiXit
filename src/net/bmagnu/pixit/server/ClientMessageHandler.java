package net.bmagnu.pixit.server;

import org.json.simple.JSONObject;

public interface ClientMessageHandler {
	public default JSONObject handle(JSONObject data, GameServer server, ClientConnection socket) {
		return handle(data, server);
	}
	
	JSONObject handle(JSONObject data, GameServer server);
	
	@SuppressWarnings ("unchecked")
	default JSONObject getDefaultFail(String reason) {
		JSONObject fail = new JSONObject();
		fail.put("success", false);
		fail.put("failReason", reason);
		
		return fail;
	}
	
	@SuppressWarnings ("unchecked")
	default JSONObject getDefaultSuccess() {
		JSONObject success = new JSONObject();
		success.put("success", true);
		
		return success;
	}
}
