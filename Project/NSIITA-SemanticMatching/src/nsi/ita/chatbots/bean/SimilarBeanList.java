package nsi.ita.chatbots.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用以解决SimilarBean对象相似度操作的一些问题
 * 继承自ArrayList
 */
public class SimilarBeanList extends ArrayList<SimilarBean>{
	
	private static final long serialVersionUID = -2690763183089750887L;
	
	@Override
	public int size() {
		// TODO 自动生成的方法存根
		return super.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO 自动生成的方法存根
		return super.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		// TODO 自动生成的方法存根
		return super.contains(o);
	}

	@Override
	public int indexOf(Object o) {
		// TODO 自动生成的方法存根
		return super.indexOf(o);
	}

	@Override
	public SimilarBean get(int index) {
		// TODO 自动生成的方法存根
		return super.get(index);
	}

	@Override
	public boolean add(SimilarBean e) {
		// TODO 自动生成的方法存根
		return super.add(e);
	}

	@Override
	public SimilarBean remove(int index) {
		// TODO 自动生成的方法存根
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		// TODO 自动生成的方法存根
		return super.remove(o);
	}

	@Override
	public void clear() {
		// TODO 自动生成的方法存根
		super.clear();
	}

	/**
	 * 获取SimilarBean列表中Similarity值最大的SimilarBean
	 * @return
	 */
	public SimilarBean getBeanByMaxSimilarity(){
		
		//保存当前相似度最大的Bean对象和序号
		double max = get(0).getSimilarity();
		int index = 0;
		
		//循环判断最大值
		for(int i=1;i<size();i++){
			if(get(i).getSimilarity() > max){
				max = get(i).getSimilarity();
				index = i;
			}
		}
		
		//最大相似度不能为0
		if(max == 0.0)
			return null;
		
		return get(index);
	}
	
	/**
	 * 获取指定的相似度范围内的Bean对象列表
	 * @param	Value 当前所给出的基准值
	 * @param	upper 上限百分比
	 * @param	lower 下限百分比
	 * @return	符合限定范围的Baen对象列表
	 */
	public List<SimilarBean> getBeanListByLimitSimilarity(SimilarBean standardBean, double upper, double lower){
		
		//用来存储相似度符合的范围的Bean对象
		List<SimilarBean> lstSB = new ArrayList<SimilarBean>();
		
		//计算出最大值和最小值
		double Value = standardBean.getSimilarity();
		double max = Value * (1-lower);
		double min = Value * (1-upper);
		
		//循环找出所有范围内(除为0外)的Bean对象并加入列表中
		for(int i=0;i<super.size();i++){
			SimilarBean bean = get(i);
			if(bean.getSimilarity() > min && bean.getSimilarity() <= max){
				if(!bean.getPhrases().equals(standardBean.getPhrases())){
					lstSB.add(get(i));
				}
			}
		}
		
//		//判断结果是否为空
//		if(lstSB.size() == 1)
//			return null;
		
		return lstSB;
	}
	
}