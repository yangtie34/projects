package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_COMMODITY_IMAGE")
public class TCommodityImage  implements java.io.Serializable {
	
		private static final long serialVersionUID = 1L;
		private String id;
		private String commodityId;
		private String imageUrl;
		private Integer order;

		/** default constructor */
		public TCommodityImage() {
		}
		
		/** minimal constructor */
		public TCommodityImage(String id) {
			this.id = id;
		}

		/** full constructor */
		public TCommodityImage(String id, String commodityId, String imageUrl,
				Integer order) {
			this.id = id;
			this.commodityId = commodityId;
			this.imageUrl = imageUrl;
			this.order = order;
		}
		
		@Id
		@Column(name = "ID", unique = true, nullable = false, length = 20)
		public String getId() {
			return this.id;
		}
		public void setId(String id) {
			this.id = id;
		}

		@Column(name = "COMMODITY_ID", length = 50)
		public String getCommodityId() {
			return this.commodityId;
		}

		public void setCommodityId(String commodityId) {
			this.commodityId = commodityId;
		}

		@Column(name = "IMAGE_URL", length = 3000)
		public String getImageUrl() {
			return this.imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		@Column(name = "ORDER_", length = 2)
		public Integer getOrder() {
			return this.order;
		}

		public void setOrder(Integer order) {
			this.order = order;
		}

}
