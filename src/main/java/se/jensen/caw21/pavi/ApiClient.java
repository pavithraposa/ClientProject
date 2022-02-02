package se.jensen.caw21.pavi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ApiClient {

    private String apiAddress;
    HttpURLConnection connection;

    public ApiClient(String apiAddress) {
        this.apiAddress = apiAddress;
    }

    public ArrayList<String> getStringArray(String target) {
        JsonObject countryObj = new JsonObject();
        ArrayList<String> myArrayOfStrings = new ArrayList<>();
        return myArrayOfStrings;
    }

    public Blog[] getBlog() {
        Blog[] blog = {};

        String target = "/blog/list";
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);

                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);

                }
                reader.close();
            }

            String jsonStr = responseContent.toString();

            ObjectMapper mapper = new ObjectMapper();

            blog = mapper.readValue(jsonStr, Blog[].class);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return blog;
    }

     public Blog getBlogbyId (int id) {
         Blog blog = null;

         String target = "/blog/view/"+id;
         BufferedReader reader;
         String line;
         StringBuilder responseContent = new StringBuilder();
         //boolean success = false;

         try {
             URL url = new URL(apiAddress + target);
             connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             connection.setRequestProperty("accept", "application/json");

             int status = connection.getResponseCode();

             if (status >= 300) {
                 reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                 while ((line = reader.readLine()) != null) {
                     responseContent.append(line);
                     System.out.println("Issue finding ID");

                 }
                 reader.close();
             } else {
                 reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 while ((line = reader.readLine()) != null) {
                     responseContent.append(line);

                 }
                 reader.close();
             }

             if(status==200){
                 String jsonStr = responseContent.toString();
                 ObjectMapper mapper = new ObjectMapper();
                 blog = mapper.readValue(jsonStr, Blog.class);
             }
         } catch (Exception e) {
             System.out.println("Exception: " + e);
         } finally {
             connection.disconnect();
         }

         return blog;
     }

    public boolean deleteBlog() {

        String target = "/blog/clear";
        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();

            if (status < 300) {
                success = true;

            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }

    public Blog updateBlogbyId(int id) {
        Blog blog = new Blog();
        String target = "/blog/update/" + id;
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

                    //boolean success = false;
        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                    System.out.println("Issue finding ID");

                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);

                }
                reader.close();
            }

            if (status == 200) {
                try (OutputStream os = connection.getOutputStream()) {

                     byte[] input = blog.toJson().getBytes(StandardCharsets.UTF_8);

                     os.write(input, 0, input.length);
                }

                 /*String jsonStr = responseContent.toString();
                ObjectMapper mapper = new ObjectMapper();
                blog = mapper.readValue(jsonStr, Blog.class); */
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return blog;
   }

    public Blog deleteBlogbyId(int id) {
        Blog blog=null;

        String target = "/blog/delete/" + id; // http://127.0.0.1:8080/api/v1/blog/delete

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();
            if(status>300)    {
                System.out.println("Issue deleting Id");
            }

            if (status < 300) {
                success = true;

            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return blog;
    }                    //deleteBlogbyId(int id)


    public boolean addBlog(Blog newBlog) {
        String target = "/blog/create";
        boolean success = false;

        try {

            URL url = new URL(apiAddress + target);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {

                byte[] input = newBlog.toJson().getBytes(StandardCharsets.UTF_8);

                os.write(input, 0, input.length);
            }

            int status = connection.getResponseCode();

            if (status < 300) {
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }
        return success;
    }
}

