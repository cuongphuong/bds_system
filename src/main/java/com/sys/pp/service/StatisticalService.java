package com.sys.pp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import com.sys.pp.controller.custommodel.StatisticalResult;

@Service("StatisticalService")
public class StatisticalService {
	public enum Type {
		NEW_TYPE("1"), //
		BDS_CATEGORY("2"), //
		PROVINCE("3"), //
		PROJECT("4");

		String id;

		Type(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
	}

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<StatisticalResult> statistical(Date dateFrom, Date dateTo, List<String> list, Type type) {
		EntityManager session = entityManagerFactory.createEntityManager();
		try {
			String sql = "";
			switch (type) {
			case NEW_TYPE:
				sql = this.makeSqlForStatisticalByNewsType(list);
				break;
			case BDS_CATEGORY:
				sql = this.makeSqlForStatisticalByCategory(list);
				break;
			case PROVINCE:
				sql = this.makeSqlForStatisticalByProvince(list);
				break;
			case PROJECT:
				sql = this.makeSqlForStatisticalByProject(list);
				break;
			}

			Query query = session.createNativeQuery(sql);

			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			if (!ListUtils.isEmpty(list) && list.size() > 0 && list.size() > 0) {
				query.setParameter("format", list);
			}

			query.unwrap(NativeQuery.class).addScalar("label", StringType.INSTANCE)
					.addScalar("price", StringType.INSTANCE).addScalar("newsNumber", StringType.INSTANCE)
					.addScalar("vat", StringType.INSTANCE).addScalar("finalPrice", StringType.INSTANCE)
					.setResultTransformer(Transformers.aliasToBean(StatisticalResult.class));

			List<StatisticalResult> results = (List<StatisticalResult>) query.getResultList();
			return results;
		} catch (Exception e) {
			return new ArrayList<>();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

	private String makeSqlForStatisticalByProvince(List<String> list) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" 	province_id as label, ");
		sql.append("     sum(b.price) as price,");
		sql.append("     count(d.news_id) as newsNumber,");
		sql.append("     (sum(b.price) * 0.01) as vat,");
		sql.append("     sum(b.price) - (sum(b.price) * 0.01) as finalPrice");
		sql.append(" from `bds_ news` b ");
		sql.append(" 	inner join detail_news d ");
		sql.append("     on b.news_id = d.news_id");
		sql.append(" where b.create_at between :dateFrom and :dateTo");
		sql.append(" 	and status_flg = 1 ");
		if (!ListUtils.isEmpty(list) && list.size() > 0) {
			sql.append(" and d.formality in (:format)");
		}
		sql.append(" group by province_id ");

		return sql.toString();
	}

	private String makeSqlForStatisticalByNewsType(List<String> list) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" 	 level as label, ");
		sql.append("     sum(b.price) as price,");
		sql.append("     count(d.news_id) as newsNumber,");
		sql.append("     (sum(b.price) * 0.01) as vat,");
		sql.append("     sum(b.price) - (sum(b.price) * 0.01) as finalPrice");
		sql.append(" from `bds_ news` b ");
		sql.append(" 	inner join detail_news d ");
		sql.append("     on b.news_id = d.news_id");
		sql.append(" where b.create_at between :dateFrom and :dateTo");
		sql.append(" 	and status_flg = 1 ");
		if (!ListUtils.isEmpty(list) && list.size() > 0) {
			sql.append(" and d.formality in (:format)");
		}
		sql.append(" group by level ");

		return sql.toString();
	}

	private String makeSqlForStatisticalByProject(List<String> list) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" 	 d.project_id as label, ");
		sql.append("     sum(b.price) as price,");
		sql.append("     count(d.news_id) as newsNumber,");
		sql.append("     (sum(b.price) * 0.01) as vat,");
		sql.append("     sum(b.price) - (sum(b.price) * 0.01) as finalPrice");
		sql.append(" from `bds_ news` b ");
		sql.append(" 	inner join detail_news d ");
		sql.append("     on b.news_id = d.news_id");
		sql.append(" where b.create_at between :dateFrom and :dateTo ");
		sql.append(" 	and d.project_id is not null ");
		sql.append(" 	and status_flg = 1 ");
		if (!ListUtils.isEmpty(list) && list.size() > 0) {
			sql.append(" and d.formality in (:format)");
		}
		sql.append(" group by d.project_id ");

		return sql.toString();
	}

	private String makeSqlForStatisticalByCategory(List<String> list) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" 	 category_id as label, ");
		sql.append("     sum(b.price) as price,");
		sql.append("     count(d.news_id) as newsNumber,");
		sql.append("     (sum(b.price) * 0.01) as vat,");
		sql.append("     sum(b.price) - (sum(b.price) * 0.01) as finalPrice");
		sql.append(" from `bds_ news` b ");
		sql.append(" 	inner join detail_news d ");
		sql.append("     on b.news_id = d.news_id");
		sql.append(" where b.create_at between :dateFrom and :dateTo");
		sql.append(" 	and status_flg = 1 ");
		if (!ListUtils.isEmpty(list) && list.size() > 0) {
			sql.append(" and d.formality in (:format)");
		}
		sql.append(" group by category_id ");

		return sql.toString();
	}
}
