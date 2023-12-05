/*
public class Error extends Exception{

    private String message = "";

    public Error(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public boolean registoInválido(){

        switch(message){
            case "Email não pode conter uma vígula.",
                    "Esse email já está registado, por favor faça o login" + "\n" + "Para fazer login, basta escrever ','",
                    "Password não pode conter uma vígula.",
                    "Passwords não são iguais.",
                    "Registo feito com sucesso!"
                    ->{return false;}
            default -> {return true;}
        }


    }

}
*/