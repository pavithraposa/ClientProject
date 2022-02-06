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

import org.springframework.web.reactive.function.client.WebClient;
  import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;










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

    public Blog getBlogbyId(int id) {
        Blog blog = null;

        String target = "/blog/view/" + id;
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

            if (status == 200) {
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

    public Blog updateBlogbyId(Blog updateBlog, int id) {
        //Blog blog=null;

        String target = "/blog/update/" + id; // http://127.0.0.1:8080/api/v1/blog/delete
        System.out.println(apiAddress + target);
        //System.out.println(updateBlog);

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            //connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                if (connection.getOutputStream() != null) {
                    byte[] input = updateBlog.toJson().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int status = connection.getResponseCode();
            System.out.println("Status: " + status);
            if (status < 300) {
                success = true;

            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return updateBlog;
    }                    //deleteBlogbyId(int i

    public Blog deleteBlogbyId(int id) {
        Blog blog = null;

        String target = "/blog/delete/" + id; // http://127.0.0.1:8080/api/v1/blog/delete

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();
            if (status > 300) {
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

     public boolean exitBlog() {
          String target = "/blog/exit";
          boolean success = false;

          try {
              URL url = new URL(apiAddress + target);
              connection = (HttpURLConnection) url.openConnection();
              connection.setRequestMethod("GET");

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


