package se.jensen.caw21.pavi;

import java.util.Scanner;

public class ClientProgram {
    private final ApiClient myApiClient;

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
            System.out.println("3. Update a Blog details by Id.");
            System.out.println("4. Get Blog details by Id.");
            System.out.println("5. Delete a specific Blog by Id.");
            System.out.println("6. Clear all the Blogs list.");
            System.out.println("7. Exit program");
            System.out.println("=========================================");
            System.out.println();

            int userChoice = getUserInt();
            System.out.println("User choice: " + userChoice);
            switch (userChoice) {
                case 1 -> createNewBlog();
                case 2 -> printListOfBlogs();
                case 3 -> updateBlogById();
                case 4 -> searchBlogById();
                case 5 -> deleteBlogById();
                case 6 -> deleteAllBlogs();
                case 7 -> {
                    exitBlog();
                    program = false;
                }
            }
        }
    }

    private void createNewBlog() {
        System.out.println("Create NewBlog");
        System.out.println("--------------");

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

    private void printListOfBlogs() {
        System.out.println("Print List Of Blogs");
        Blog[] blog = myApiClient.getBlog();

        System.out.println("Blog");
        System.out.println("-----------------------------------------");

        if (blog.length > 0) {
            for (Blog value : blog) {
                String title = value.title;
                String author = value.author;
                String description = value.description;
                int id = value.id;

                System.out.println("ID: " + id);
                System.out.println("--------------------------------------------");
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("Author: " + author);
                System.out.println();
            }
        } else {
            System.out.println("No Blogs in list :(");
        }
    }

    private void updateBlogById() {
        System.out.println("update Blog By Id");
        System.out.println("Enter Id:");
        int id = getUserInt();
        Blog updateBlog = myApiClient.getBlogbyId(id);

        if (updateBlog != null) {

            System.out.println("What do you want to update in Blog?");
            System.out.println("1.Title");
            System.out.println("2.Description");
            System.out.println("3.Author");
            int userChoice = getUserInt();
            if (userChoice == 1) {
                System.out.println("Update the Title:");
                String title = getUserString();
                updateBlog.setTitle(title);

            } else if (userChoice == 2) {
                System.out.println("Update Description");
                String description = getUserString();
                updateBlog.setDescription(description);

            } else if (userChoice == 3) {
                System.out.println("Update AuthorName");
                String author = getUserString();
                updateBlog.setAuthor(author);
            }
            int blogId= updateBlog.getId();
            myApiClient.updateBlogbyId(updateBlog, id);
            System.out.println("Blog Id: " + blogId + " has been updated");
        } else {
            System.out.println("There is no Blog with id ' " + id +" ' in the list.");
        }
    }

    private void searchBlogById() {
        System.out.println("Search Blog By ID");
        System.out.println("Enter Id:");
        int id=getUserInt();
        Blog blog = myApiClient.getBlogbyId(id);

        if (blog != null) {

            String title = blog.title;
            String author = blog.author;
            String description = blog.description;

            System.out.println();
            System.out.println("ID: " + id);
            System.out.println("--------------------------------------------");
            System.out.println( "Title: "+ title);
            System.out.println("Description: " +description);
            System.out.println("Author: "+ author);
            System.out.println();

        } else{
            System.out.println("There is no Blog with id ' " + id +" ' in the list.");
        }
    }

    private void deleteBlogById() {
        System.out.println("Delete Blog BY ID");
        System.out.println("What is the Id:");
        int deletingid = getUserInt();
        Blog blog= myApiClient.getBlogbyId(deletingid);

        if(blog !=null ){
            myApiClient.deleteBlogbyId(deletingid);
            System.out.println("The Blog is deleted Successfully");
        }
        else {
            System.out.println("There is no Blog with id " + deletingid +" in the list.");
            System.out.println("so, Issue deleting the Blog");
        }
    }

    private void deleteAllBlogs() {

        if(myApiClient.deleteBlog()){
            System.out.println("Clearing list Successfully");
        }
        else{
            System.out.println("Issue with files :(");
        }
    }

    private void exitBlog() {

        if(myApiClient.exitBlog()){
            System.out.println("Exiting the program");
            System.out.println("BYE BYE!!");
        }
        else{
            System.out.println("Issue saving file  :(");
            System.out.println("Exiting the program");
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

