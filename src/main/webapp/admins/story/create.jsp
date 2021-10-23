<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />
		
		
		
		<div class="row py-3">
			<div class="col-4"><h3>ストーリー新規作成</h3> </div>
		</div>
		
		
		
		
		<form action="./create" method="post">
			<table class="table">
				<tr>
					<th class="bg-light">タイトル</th>
					<td>
						<input class="form-control" type="text" name="new_title"  required autofocus> 			
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">本文</th>
					<td>
						<textarea  name="new_sentence" required cols="80" rows="10" class="form-control"></textarea>
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">問題(後日実装)</th>
					<td>
						<select name="new_eid" class="form-control" required>
							<option value="">選択してください</option>	
							<option value="0">あとで決める</option>									
						</select>
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">次のストーリーのタイトル</th>
					<td>
						<select name="new_next_title" class="form-control" required>
							<option value="">選択してください</option>
							<option value="あとで決める">あとで決める</option>
							
							<% story_dao.AdminStoryIndexDAO asid = new story_dao.AdminStoryIndexDAO(); %>
							<% java.util.List<beans.AdminStoryBeans> asbs = asid.findAllStory(); %>
							<% for(int i = asbs.size() -1; i >=0; i--) { %>
								<option value=<%= asbs.get(i).getTitle() %>><%= asbs.get(i).getTitle() %></option>
							<% } %>
											
						</select>
					</td>
				</tr>
				
				<tr>
					<th></th>
					<td><input type="submit" value="投稿" class="btn btn-success"></td></tr>
			</table>
			
			
		</form>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>