package cn.gilight.personal.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_LIAO_TOPIC_IMAGE")
public class TLiaoTopicImage implements java.io.Serializable {
	private static final long serialVersionUID = -6643903150681456184L;
	private String id;
	private String topicId;
	private String imgUrl;
	private Date createTime;

	// Constructors

	/** default constructor */
	public TLiaoTopicImage() {
	}

	/** minimal constructor */
	public TLiaoTopicImage(String id) {
		this.id = id;
	}

	/** full constructor */
	public TLiaoTopicImage(String id, String topicId, String imgUrl,
			Date createTime) {
		this.id = id;
		this.topicId = topicId;
		this.imgUrl = imgUrl;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TOPIC_ID", length = 50)
	public String getTopicId() {
		return this.topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	@Column(name = "IMG_URL", length = 3000)
	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}