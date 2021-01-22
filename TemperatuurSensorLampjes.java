package temperatuursensorlampjes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class TemperatuurSensorLampjes
{

    private static HttpURLConnection connection;
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try
        {
            URL url = new URL("http://192.168.2.15:5000/api/lamps");
            connection = (HttpURLConnection) url.openConnection();
            
            // Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int status = connection.getResponseCode();
            System.out.println(status);
            
            if (status > 299)
            {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null)
                {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                while((line = reader.readLine()) != null)
                {
                    responseContent.append(line);
                }
            }
//            System.out.println(responseContent.toString());
            parse(responseContent.toString());
            
        } catch (MalformedURLException e)
        {
        } catch (IOException e) {
        } finally {
            connection.disconnect();
        }
    }
    
    public static String parse(String responseBody)
    {
        JSONArray lamps = new JSONArray(responseBody);
        for(int i = 0; i < lamps.length(); i++)
        {
            JSONObject lamp = lamps.getJSONObject(i);
            int id = lamp.getInt("id");
            int status = lamp.getInt("status");
            System.out.println(" id " + id + " Status:" + status);
        }
        return null;
    }
    
    
}
