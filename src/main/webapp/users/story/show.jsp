<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />
		
		<!-- 該当のストーリーを検索 -->
		<% String title = request.getParameter("title"); %>
		<% int uid = ((beans.UserAccountBeans) (request.getSession(false).getAttribute("user"))).getUid(); %>
		<% beans.UserStoryBeans usb = story_dao.UserStoryShowDAO.findStory(title, uid); %>
		
		<!-- eid取得 -->
		<% int eid = usb.getEid(); %>
		
		<!-- 該当の問題を検索 -->
		<% beans.UserExerciseBeans ueb = exercise_dao.UserExerciseShowDAO.findExercise(eid, uid); %>
		
		<% // 閲覧履歴をつける
		if (!usb.getIsOpened() && !usb.getIsFocused()) {
			story_dao.UserStoryViewsDAO.setViewStory(title, uid, true);
			usb.setIsOpened(true);
			usb.setIsFocused(false);
		}%>
		
		<div class="row py-3">
			<div class="col-9"><h3><%= usb.getTitle() %></h3> </div>	
			<div class="col-3">
				<% System.out.println(usb.getIsOpened()); %>
				<% if (usb.getIsOpened()) { %>
					<form action="./show" method="get">
						<input type="hidden" name="title" value="<%= usb.getTitle() %>">
						<input type="hidden" name="new_is_opened" value="false">
						<input type="submit" value="チェックをつける" class="btn btn-secondary">
						<i class="far fa-flag text-primary"></i>
					</form>
				<% } else { %>
					<form action="./show" method="get">
						<input type="hidden" name="title" value="<%= usb.getTitle() %>">
						<input type="hidden" name="new_is_opened" value="true">
						<input type="submit" value="チェックを外す" class="btn btn-primary">
						<i class="fas fa-flag text-primary"></i>
					</form>
				<% } %>
				
			</div>
		</div>
		
		
		<div class="row py-3">
			<div class="col-1"></div>
			<div class="col-10"><%= usb.getSentence() %></div>
		</div>
		
		<% if (ueb != null) { %>
		
			<div class="row py-3">
				<div class="col-4"><h3>練習問題</h3></div>
			</div>
			
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10"><%= ueb.getSentence() %></div>
			</div>
			
			<div class="row py-3">
				<form action="./show?title=<%= usb.getTitle() %>" method="post" class="col-6">
					<div class="field py-3">
						<label class="font-weight-bold">解答</label><br>
						
							<% java.util.Map<String, Object> result_map = (java.util.HashMap<String, Object>) request.getSession(false).getAttribute("result_map"); %>
							
							<% if(result_map != null) { %>
								<textarea  name="my_answer" required cols="70" rows="10" class="form-control"><%= result_map.get("my_answer")  %></textarea>
							<% } else { %>
								<textarea  name="my_answer" required cols="70" rows="10" class="form-control"></textarea>
							<% } %>
					</div>
						
					<div class="py-3">
						<input type="submit" value="解答" class="btn btn-success">
					</div>
				</form>
				<div class="col-5 py-3">
					<label class="font-weight-bold">実行結果</label><br>
					<div>
						<% if (result_map != null) { %>
							<% if (result_map.get("result") instanceof String) { %>
								<span class="text-danger">
									<%= result_map.get("result") %>
								</span>
							<% } else if (result_map.get("result") instanceof java.util.ArrayList) { %>
								<% java.util.ArrayList<java.util.ArrayList<String>> result_array = (java.util.ArrayList<java.util.ArrayList<String>>) result_map.get("result"); %>
								<table class="table table-bordered">
									<tr>
										<% for(int i = 0; i < result_array.get(0).size(); i++) { %>
											<th class="bg-light"><%= result_array.get(0).get(i) %></th>
										<% } %>
									</tr>
									
									<% for(int i = 1; i < result_array.size(); i++) { %>
										<tr>
											<% for(int j = 0; j < result_array.get(i).size(); j++) { %>
												<td><%= result_array.get(i).get(j) %></td>
											<% } %>
										</tr>
									<% } %>
									
								</table>
							<% } %>
						<% } else {%>
							<% System.out.println("result_map = " + result_map); %>
						<% } %>
					</div>
				</div>
			</div>
			
			<% if (request.getSession(false).getAttribute("result_map") != null) {
				request.getSession(false).removeAttribute("result_map");
			} %>
		<% } %>
		
				
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>