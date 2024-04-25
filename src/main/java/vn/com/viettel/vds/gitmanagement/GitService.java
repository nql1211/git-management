package vn.com.viettel.vds.gitmanagement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.viettel.vds.gitmanagement.application.service.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;
import java.io.IOException;

public class GitService{

    public static void main(String[] args) {

        String baseUrl = "http://gitlab.orc.com/api/v4/";
        String token = "glpat-d2BBx28oX7tDE3KQyjsx";
        String projectName = "test1123";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitApi service = retrofit.create(GitApi.class);

        ProjectReq request = new ProjectReq(projectName);

        Call<Project> call = service.createProject(token, request);
        
        call.enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()) {
                    Project repository = response.body();
                    if (repository != null)
                        System.out.println("Repository created: " + repository.getHttpUrl());
                } else {
                    try {
                        System.out.println("Error: " + response.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
