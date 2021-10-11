package team.culturecomputing.bupt.dao;

import java.util.List;


public class SimImageInfo {
		private List<String> imagePath;
		private List<String> imageSimValue;
		public void setImagePath(List<String> imagePath)
		{
			this.imagePath=imagePath;
			
		}
		public List<String> getImagePath() {
			return this.imagePath;
		}
		public void setImageSimValue(List<String> imageSimValue)
		{
			this.imageSimValue=imageSimValue;
			
		}
		public List<String> getImageSimValue() {
			return this.imageSimValue;
		}

}
