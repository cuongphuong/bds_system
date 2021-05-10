package com.sys.pp.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import com.sys.pp.constant.GemRealtyConst.AcreageScope;
import com.sys.pp.constant.GemRealtyConst.FontWidth;
import com.sys.pp.constant.GemRealtyConst.PriceScope;
import com.sys.pp.controller.custommodel.SearchCondition;
import com.sys.pp.model.BdsNew;

@Service("SearchService")
public class SearchService {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@SuppressWarnings("unchecked")
	public List<BdsNew> searchData(SearchCondition searchCondition) {
		EntityManager session = entityManagerFactory.createEntityManager();
		try {
			String sql = this.makeSqlForSearch(searchCondition);
			Query query = session.createNativeQuery(sql, BdsNew.class);

			query.setParameter("keyword", "%" + searchCondition.getKeyword() + "%");

			if (!ListUtils.isEmpty(searchCondition.getFormalityList())) {
				query.setParameter("formality", searchCondition.getFormalityList());
			}
			if (!ListUtils.isEmpty(searchCondition.getCategoryList())) {
				query.setParameter("category_id", searchCondition.getCategoryList());
			}
			if (searchCondition.getLocation() != null) {
				query.setParameter("province_id", searchCondition.getLocation());
			}
			if (!ListUtils.isEmpty(searchCondition.getDistrictList())) {
				query.setParameter("district_id", searchCondition.getDistrictList());
			}
			if (!ListUtils.isEmpty(searchCondition.getWardList())) {
				query.setParameter("ward_id", searchCondition.getDistrictList());
			}
			if (!ListUtils.isEmpty(searchCondition.getStreetList())) {
				query.setParameter("street_id", searchCondition.getStreetList());
			}
			if (!ListUtils.isEmpty(searchCondition.getProjectList())) {
				query.setParameter("project_id", searchCondition.getProjectList());
			}
			if (!ListUtils.isEmpty(searchCondition.getProvinceList())) {
				query.setParameter("n_province_ids", searchCondition.getProvinceList());
			}
			if (searchCondition.getPrice() != null) {
				PriceScope price = searchCondition.getPrice();

				if (price.getId() != 0) {
					query.setParameter("priceFrom", price.getStart());
					query.setParameter("priceTo", price.getEnd());
				}
			}
			if (searchCondition.getAcreage() != null) {
				AcreageScope acreage = searchCondition.getAcreage();
				if (acreage.getStart() >= 0) {
					query.setParameter("acreageFrom", acreage.getStart());
				}
				if (acreage.getEnd() >= 0) {
					query.setParameter("acreageTo", acreage.getEnd());
				}
			}
			if (searchCondition.getFrontWidthList() != null) {
				for (int i = 0; i < searchCondition.getFrontWidthList().size(); i++) {
					FontWidth item = searchCondition.getFrontWidthList().get(0);
					query.setParameter(String.format("frontFrom%s", i), item.getStart());
					query.setParameter(String.format("frontTo%s", i), item.getEnd());
				}
			}
			if (searchCondition.getFloorList() != null) {
				for (int i = 0; i < searchCondition.getFloorList().size(); i++) {
					query.setParameter(String.format("floor%s", i), searchCondition.getFloorList().get(i));
				}
			}
			if (searchCondition.getRoomList() != null) {
				for (int i = 0; i < searchCondition.getRoomList().size(); i++) {
					query.setParameter(String.format("room%s", i), searchCondition.getRoomList().get(i));
				}
			}
			if (searchCondition.getWayList() != null) {
				for (int i = 0; i < searchCondition.getWayList().size(); i++) {
					query.setParameter(String.format("way%s", i), searchCondition.getWayList().get(i));
				}
			}

			List<BdsNew> results = (List<BdsNew>) query.getResultList();
			return results;
		} catch (Exception e) {
			return new ArrayList<BdsNew>();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

	public String makeSqlForSearch(SearchCondition searchCondition) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT bds.* ");
		sql.append(" FROM `bds_ news` bds ");
		sql.append(" INNER JOIN detail_news dt ON bds.news_id = dt.news_id ");
		sql.append(" WHERE bds.title like :keyword ");
		sql.append("   AND bds.status_flg = 1 ");
		sql.append("   AND bds.delete_flg <> 1 ");
		sql.append("   AND SYSDATE() <= bds.end_date ");
		sql.append("   AND SYSDATE() >= bds.start_date ");

		if (!ListUtils.isEmpty(searchCondition.getFormalityList())) {
			sql.append("   AND dt.formality in (:formality) ");
		}
		if (!ListUtils.isEmpty(searchCondition.getCategoryList())) {
			sql.append("   AND bds.category_id in (:category_id) ");
		}
		if (!ListUtils.isEmpty(searchCondition.getProvinceList())) {
			sql.append("   AND dt.province_id in (:n_province_ids) ");
		}
		if (searchCondition.getLocation() != null) {
			sql.append("   AND dt.province_id = :province_id ");
		}
		if (!ListUtils.isEmpty(searchCondition.getDistrictList())) {
			sql.append("   AND dt.district_id in (:district_id) ");
		}
		if (!ListUtils.isEmpty(searchCondition.getWardList())) {
			sql.append("   AND dt.ward_id in (:ward_id) ");
		}
		if (!ListUtils.isEmpty(searchCondition.getStreetList())) {
			sql.append("   AND dt.street_id in (:street_id) ");
		}
		if (!ListUtils.isEmpty(searchCondition.getProjectList())) {
			sql.append("   AND dt.project_id in (:project_id) ");
		}
		if (searchCondition.getPrice() != null) {
			PriceScope price = searchCondition.getPrice();

			if (price.getId() == 0) {
				sql.append("   AND dt.price is null ");
			} else {
				sql.append("   AND (CASE ");
				sql.append("            WHEN dt.unit = 1 THEN dt.price * 1000 ");
				sql.append("            ELSE dt.price ");
				sql.append("        END) >= :priceFrom ");
				sql.append("   AND (CASE ");
				sql.append("            WHEN dt.unit = 1 THEN dt.price * 1000 ");
				sql.append("            ELSE dt.price ");
				sql.append("        END) <= :priceTo ");
			}
		}
		if (searchCondition.getAcreage() != null) {
			AcreageScope acreage = searchCondition.getAcreage();
			if (acreage.getStart() >= 0) {
				sql.append("   AND dt.acreage >= :acreageFrom ");
			}
			if (acreage.getEnd() >= 0) {
				sql.append("   AND dt.acreage <= :acreageTo ");
			}
		}
		if (searchCondition.getFrontWidthList() != null) {
			List<String> subs = new ArrayList<String>();
			for (int i = 0; i < searchCondition.getFrontWidthList().size(); i++) {
				subs.add(String.format(" (dt.front_width >= :frontFrom%s AND dt.front_width <= :frontTo%s) ", i, i));
			}
			sql.append(" AND ( ");
			sql.append(String.join("OR", subs));
			sql.append(" )");
		}
		if (searchCondition.getFloorList() != null) {
			List<String> subs = new ArrayList<String>();
			for (int i = 0; i < searchCondition.getFloorList().size(); i++) {
				subs.add(String.format(" (dt.floors_num = :floor%s) ", i));
			}
			sql.append(" AND ( ");
			sql.append(String.join("OR", subs));
			sql.append(" )");
		}
		if (searchCondition.getRoomList() != null) {
			List<String> subs = new ArrayList<String>();
			for (int i = 0; i < searchCondition.getRoomList().size(); i++) {
				subs.add(String.format(" (dt.room_num = :room%s) ", i));
			}
			sql.append(" AND ( ");
			sql.append(String.join("OR", subs));
			sql.append(" )");
		}
		if (searchCondition.getWayList() != null) {
			List<String> subs = new ArrayList<String>();
			for (int i = 0; i < searchCondition.getWayList().size(); i++) {
				subs.add(String.format(" (dt.entrance_width > :way%s) ", i));
			}
			sql.append(" AND ( ");
			sql.append(String.join("OR", subs));
			sql.append(" )");
		}
		return sql.toString();
	}
}
