package resultMaker;


import resultMaker.WriterStrategy;
import java.io.File;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import resultStructureModel.PatternsGroupInfo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amirEbrahimi.um@gmail.com
 */
public class ResultViewer 
{

    private PatternsGroupInfo patternsGroupInfo;
    private WriterStrategy writerStrategy;
    
    public ResultViewer(PatternsGroupInfo patternsGroupInfo)
    {
        this.patternsGroupInfo = patternsGroupInfo;  
    }

    public ResultViewer(PatternsGroupInfo patternsGroupInfo, WriterStrategy writerStrategy) {
        this.patternsGroupInfo = patternsGroupInfo;
        this.writerStrategy = writerStrategy;
    }

    
    public WriterStrategy getWriterStrategy() {
        return writerStrategy;
    }

    public void setWriterStrategy(WriterStrategy writerStrategy) {
        this.writerStrategy = writerStrategy;
    }

    public void viewResults()
    {
        System.out.println(writerStrategy.makeResult(patternsGroupInfo));
    }
    
    public String getResults()
    {
        return writerStrategy.makeResult(patternsGroupInfo);
    }
    
    
    

}
