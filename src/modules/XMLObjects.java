package modules;

import java.util.Map;

import modules.Common.EHotDotCouponStates;

public class XMLObjects {

	public class HotDotCouponItem {
		private Map<String, Integer> position;
		private Map<EHotDotCouponStates, Boolean> status;
		
		public Map<String, Integer> getPosition() {
			return position;
		}

		public void setPosition(Map<String, Integer> position) {
			this.position = position;
		}

		public Map<EHotDotCouponStates, Boolean> getStatus() {
			return status;
		}

		public void setStatus(Map<EHotDotCouponStates, Boolean> status) {
			this.status = status;
		}
	}	
}

