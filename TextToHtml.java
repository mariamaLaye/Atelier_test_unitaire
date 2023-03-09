import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextToHtml {


    public TextToHtml(){}

    public String getFileContent(String fileName){
        StringBuilder res = new StringBuilder();
        try
        {
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while((line = br.readLine()) != null)
            {
                res.append(line.trim()).append("\n");
            }
            fr.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return res.toString();
    }

    public void writeFile(String fileName, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        writer.append(content);
        writer.close();
    }

    public HashMap<String, String> getTxtFileContentToDico(String content){
        
        HashMap<String, String> res = new HashMap<String, String>();
        String[] contentList = content.trim().split("\n");

        for(String line : contentList){

            StringBuffer sb = new StringBuffer(line.split(":")[1]);

            String key = line.split(":")[0];
            String value = sb.deleteCharAt(sb.length() - 1).toString();
            
            res.put(key, value);
        }

        return res;
    }

    public String getGearListHtml(HashMap<String, String> dicoInfos){
        String res = "";
        for(Map.Entry<String, String> entry : dicoInfos.entrySet()){
            res += String.format("<label>%s</label><br>", entry.getValue());
        }
        return res;
    }
    
    public String getAllAgentstoHtml(String path){
        String res = "";
        File dirStaff = new File(path); 

        for(File file : dirStaff.listFiles()){
            Agent currentAgent = new Agent();
            currentAgent.fillData(file.toPath().toString()); 
            res += String.format(
                "<tr><td>%s</td><td>%s</td><td>%s</td><td><button><a href=\"fiches_agents/%s.html\">  Clic  </a></button></td></tr><tr>",
                currentAgent.getIdentification(),
                currentAgent.getNom(),
                currentAgent.getPrenom(),
                currentAgent.getIdentifiant()
                );
        }
        return res;
    }   

    public boolean isExistingFileForAgentId(String path, String agent){
        File dirStaff = new File(path);
        for(File file : dirStaff.listFiles()){
            String fileNameWithoutExt = file.getName().substring(0, file.getName().indexOf(".")); 
            if(fileNameWithoutExt.equals(agent)){
                return true;
            }
        }
        return false;
    }

    public void toHtmlPage(HashMap<String, String> dicoData, String fileCat, Agent agent) throws IOException{

        String res = new String();
        
        switch(fileCat){
            case "ficheAgent":
                String gearList = getGearListHtml(dicoData);
                String checkBoxGear = "";
                for(Map.Entry<String, String> entry : dicoData.entrySet()){
                    if(!agent.getEquipements().contains(entry.getKey())){
                        checkBoxGear += "<input type=\"checkbox\" id=\"horns\" name=\"horns\"><br>";
                    }
                    else{
                        checkBoxGear += "<input type=\"checkbox\" id=\"horns\" name=\"horns\" checked=\"true\"><br>";
                    }
                }
                res = String.format(
                    "<!DOCTYPE html> " +
                    "<html> " +
                    "<head> " +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"text.css\" /> " +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"bootstrap/css/bootstrap-grid.css\" /> " +
                    "</head> " +
                    "<body> " +
                    "<div class=\"header\"> " +
                    "<div class=\"row\"> " +
                    "<div class=\"col\"> " +
                    "<img src=\"logo.png\" alt=\"Girl in a jacket\" width=\"150\" height=\"150\"> " +
                    "</div> " +
                    "<div class=\"col\"> " +
                    "<h4> Fiche Agent </h4> " +
                    "</div> " +
                    "<div class=\"col\"> " +
                    "<img src=\"profil.png\" alt=\"Girl in a jacket\" width=\"150\" height=\"150\"> " +
                    "</div> " +
                    "</div> " +
                    "</div> " +
                    "<div class=\"flex-container\"> " +
                    "<div style=\"flex-grow: 3\" id=\"flex1\"> " +
                    "%s" +
                    "</div> " +
                    "<div style=\"flex-grow: 3\" id=\"flex2\"> " +
                    "%s" +
                    "</div> " +
                    "<div style=\"flex-grow: 4\" id=\"flex3\" > " +
                    "<label for=\"scales\" id=\"flex-Identification\">Identification : %s</label><br> " +
                    "<label for=\"scales\" id=\"flex-Identification\">Prénom : %s</label><br> " +
                    "<label for=\"scales\" id=\"flex-Identification\">Nom : %s</label> " +
                    "</div> " +
                    "</div> " +
                    "</body> " +
                    "</html> ",
                    gearList,
                    checkBoxGear,
                    agent.getIdentification(),
                    agent.getPrenom(),
                    agent.getNom()
                    );
                    String agentFile = String.format("./html_page/fiches_agents/%s.html", agent.getIdentifiant()); 
                    File file = new File(agentFile);
                    if(!file.exists()){
                        file.createNewFile();
                    }writeFile(agentFile, res);
                break;
            case "listeAgent":
                res = String.format(
                    "<!DOCTYPE html> " +
                    "<html lang=\"fr\"> " +
                    "<head> " +
                    "<title>Accueil</title> " +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"Accueil.css\" /> " +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"bootstrap/css/bootstrap-grid.css\" /> " +
                    "</head> " +
                    "<body> " +
                    "<div class=\"container\"> " +
                    "<div class=\"row\"> " +
                    "<div class=\"col\"> " +
                    "<img src=\"logo.png\" alt=\"Girl in a jacket\" width=\"100\" height=\"100\"> " +
                    "</div> " +
                    "<div class=\"col\"> " +
                    "<h4> Liste des agents </h4> " +
                    "</div> " +
                    "</div> " +
                    "</div> " +
                    "<table> " +
                    "<tr> " +
                    "<th>Identifiant</th> " +
                    "<th>Nom</th> " +
                    "<th>Prénom</th> " +
                    "<th>Fiche détaillé</th>" + 
                    "</tr> " +
                    "%s" +
                    "</table> " +
                    "</body> " +
                    "</html>",
                    getAllAgentstoHtml("./datas/staff/")
                    );
                    writeFile("./html_page/listeAgent.html", res);
                break;    
        }
    }
}
