<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />

		<div class="row py-3">
			<div class="col-4"><h3>問題一覧</h3></div>
			<div class="col-5"></div>
			<div><a href="/LearnSqlServlet/admins/exercise/create.jsp" class="btn btn-success">新規作成</a></div>
		</div>
		
		<% exercise_dao.AdminExerciseIndexDAO aeid = new exercise_dao.AdminExerciseIndexDAO();  %>
		<% java.util.List<beans.AdminExerciseBeans> aeb_list = aeid.findAllExercise(); %>
		
		<table class="table">
			<tr class="row">
				<th class="col-2 bg-light">問題id</th>
				<th class="col-8 bg-light">問題文</th>
			</tr>
		
			<%for(int i = 0; i < aeb_list.size(); i++){%>
		        <% beans.AdminExerciseBeans aeb = aeb_list.get(i);%>
		        <tr class="row">
		            <td class="col-2">
		            	<a href="/LearnSqlServlet/admins/exercise/show.jsp?eid=<%= aeb.getEid() %>"><%= aeb.getEid() %></a>
		            </td>
		            
		            
		            <!-- style直指定。問題文が長すぎる場合は三点リーダーで省略 -->
		            <td style="max-width: 100%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="wrap-text col-8">
		            	<%= aeb.getSentence() %>
		            </td>
		        </tr>
		    <% } %>
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>