package se.jensen.caw21.pavi;

import java.text.AttributedCharacterIterator;
import java.util.Scanner;

public class ClientProgram {
    private ApiClient myApiClient;

    public ClientProgram() {
        myApiClient = new ApiClient("http://127.0.0.1:8080/api/v1");
    }

    public void start() {
        boolean program = true;
        while (program) {

            System.out.println();
            System.out.println("=========================================");
            System.out.println("Welcome. What would you like to do?");
            System.out.println("1. Add/Create a Blog to the list.");
            System.out.println("2. Get list of Blogs.");
            System.out.println("3. Update any specific.");
            System.out.println("4. Delete a specific Blog by Id.");
            System.out.println("5. Clear all the Blogs list.");
            System.out.println("6. Get a specific Blog details. ");
            System.out.println("7. Exit program");
            System.out.println("=========================================");
            System.out.println();

            int userChioce = getUserInt();
            System.out.println("User choice: " + userChioce);
            switch (userChioce){
                case 1:
                    createNewBlog();
                    break;
                case 2:
                    printListOfBlogs();
                    break;
                case 3:
                    updateBlogbyId();
                    break;
                case 4:
                    deleteBlogbyId();
                    break;
                case 5:
                    deleteAllBlogs();
                    break;
                case 6:
                    searchBlogbyId();
                    break;
                case 7:
                    System.out.println("Exiting the program");
                    System.out.println("BYE BYE!!");
                    program=false;
                    break;
            }

        }

    }

    private void searchBlogbyId() {
        System.out.println("Search Blog By ID");
        System.out.println("Enter Id:");
        int id=getUserInt();
        Blog blog = myApiClient.getBlogbyId(id);

        if (blog != null) {

            String title = blog.title;
            String author = blog.author;
            String description = blog.description;

            System.out.println("ID: " + id);
            System.out.println("--------------------------------------------");
            System.out.println( "Title: "+ title);
            System.out.println("Description: " +description);
            System.out.println("Author: "+ author);
            System.out.println();

        } else{
            System.out.println("There is no Blog with id" + id +"in the list.");
        }

    }


    private void updateBlogbyId() {
        System.out.println("update Blog By Id");
        System.out.println("Enter Id:");
        int id=getUserInt();
        Blog blog = myApiClient.updateBlogbyId(id);

        if(blog!=null) {

            System.out.println("Update the Title:");
            blog.title=getUserString();

            System.out.println("Update Author:");
            blog.author=getUserString();

            System.out.println("Update Description");
            blog.description=getUserString();
            id=blog.id;

            System.out.println("ID: " + id);
            System.out.println("--------------------------------------------");
            System.out.println( "Title: "+ blog.title);
            System.out.println("Description: " +blog.description);
            System.out.println("Author: "+ blog.author);
            System.out.println();
            Blog updatingBlog = new Blog(id,blog.title, blog.description, blog.author);
            //(id,blog.title, blog.author, blog.description);
            System.out.println("Blog Updated :)");

        }

        else {
            System.out.println("There is no Blog with id" + id +"in the list.");
        }

    }


    private void printListOfBlogs() {
        Blog[] blog = myApiClient.getBlog();

        System.out.println("Blog");
        System.out.println("-----------------------------------------");

        if (blog.length > 0) {
            for (int i = 0; i < blog.length; i++) {
                String title = blog[i].title;
                String author = blog[i].author;
                String description = blog[i].description;
                int id=blog[i].id;

                System.out.println("ID: " + id);
                System.out.println("--------------------------------------------");
                System.out.println( "Title: "+ title);
                System.out.println("Description: " +description);
                System.out.println("Author: "+ author);
                System.out.println();

            }
        } else {
            System.out.println("No Blogs in list :(");
        }

    }

    private void deleteAllBlogs() {

        if(myApiClient.deleteBlog()){
            System.out.println("List of Blog cleared!");
        }


        else{
            System.out.println("Issue clearing list of Blog. :(");
        }
    }

    private void deleteBlogbyId() {

        System.out.println("What is the Id:");

        //Blog[] blog = myApiClient.getBlog();
        int deletingid = getUserInt();
        Blog blog= myApiClient.getBlogbyId(deletingid);
        if(blog !=null ){
            Blog deleteBlog=myApiClient.deleteBlogbyId(deletingid);
            System.out.println("The Blog is deleted Succesfully");

        }

        else {
            System.out.println("There is no Blog with id " + deletingid +" in the list.");
            System.out.println("so, Issue deleting the Blog");
        }

    }


    private void createNewBlog() {
        System.out.println("Enter the Title:");
        String title = getUserString();

        System.out.println("Enter the AuthorName:");
        String  author = getUserString();
        System.out.println("Enter description :");
        String  description=getUserString();

        Blog newBlog = new Blog(title, description, author);

        boolean success = myApiClient.addBlog(newBlog);

        if (success) {
            System.out.println("Blog added!");
        } else {
            System.out.println("Issue adding Blog. :(");
        }
    }

    private String getUserString() {
        Scanner myScanner= new Scanner(System.in);
        String myString;

        while (true) {
            try {
                System.out.println(">");
                myString = myScanner.nextLine();
                break;
            }

            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }

        return myString;

    }

    private int getUserInt() {
        Scanner myScanner = new Scanner(System.in);
        int myInteger;
        while (true) {
            try {
                System.out.println(">");
                myInteger = Integer.parseInt(myScanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }

        }

        return myInteger;
    }


}

