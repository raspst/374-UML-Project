package problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;

public class ModifiedTxt extends AbstractObserver{
	
	public ModifiedTxt() {
		super("txt","ENTRY_MODIFY");
	}
	
	@Override
	public void update(Path file) {
				StringBuilder sb = new StringBuilder();
				try{
					BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
					String line = br.readLine();
					do{
						sb.append(" ");
						sb.append(line);
						line=br.readLine();
					}while(line!=null);
					br.close();	
				}
				catch(Exception e){
					
				}
				sb.reverse();
				System.out.println(sb.toString());
	}
	
}
