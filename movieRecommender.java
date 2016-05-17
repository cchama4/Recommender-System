package edu.uic.ids561.BigDataProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class MovieRecommend {

	public static void main(String[] args) {
		try {
			DataModel dm = new FileDataModel(new File("data/ratings.csv"));
			FileOutputStream output = new FileOutputStream("output.csv");
			TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
			
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);
			
			int x=1;
			for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
				long itemId = items.nextLong();
				List<RecommendedItem>recommendations = recommender.mostSimilarItems(itemId, 5);
				
				for(RecommendedItem recommendation : recommendations) {
					output.write(String.valueOf(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue()+"\n").getBytes());
				}
				x++;
			}
						
			
			
		} catch (IOException e) {
			System.out.println("There was an error.");
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("There was a Taste Exception");
			e.printStackTrace();
		}
		
	}
