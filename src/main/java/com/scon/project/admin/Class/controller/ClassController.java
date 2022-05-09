package com.scon.project.admin.Class.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scon.project.admin.Class.dto.ClassDTO;
import com.scon.project.admin.Class.dto.DayDTO;
import com.scon.project.admin.Class.service.ClassService;
import com.scon.project.member.model.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log테스트
@Controller
@RequestMapping("/admin")
public class ClassController {

	private ClassService classService;
	private MessageSource messageSource; // log테스트

	@Autowired
	public ClassController(ClassService classService, MessageSource messageSource) {
		this.classService = classService;
		this.messageSource = messageSource;
	}

	// 강의 등록용 화면 이동
	@GetMapping("/classRegist")
	public String getDashBoard2() {

		return "admin/class/insertClass";
	}

	/*----------------------------------------------------*/

	// 강의리스트조회
	@GetMapping("/classList")
	public ModelAndView selectClass(ModelAndView mv) {

		List<ClassDTO> classList = classService.selectClassList();
		List<DayDTO> dayList = classService.selectDayList();

		mv.addObject("classList", classList);
		mv.addObject("dayList", dayList);
		mv.setViewName("admin/class/selectClass");
		return mv;
	}

	// 강의상세조회
	@GetMapping("/classDetail")
	@ResponseBody // clssDTO 반환, JSON 컴벌트
	public ClassDTO selectClassDetail(@RequestParam int clsId) {

		ClassDTO classDetail = classService.classDetail(clsId);

		return classDetail;
	}

	@PostMapping("classRegist")
	public String registClass(@ModelAttribute ClassDTO classDTO, RedirectAttributes rttr, Locale locale)
			throws Exception {

		log.info("등록 요청 강의 : {}", classDTO);
		MemberDTO member = new MemberDTO();
		member.setId("director");
		classDTO.setMember(member);
		classService.registClass(classDTO);

		rttr.addFlashAttribute("successMessage", messageSource.getMessage("RegistClass", null, locale));

		return "redirect:/admin/classList";

	}

	@PostMapping(value = "member", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<MemberDTO> findMemberList(int clsId) {
		return classService.findAllmemberList(clsId);
	}

	// 강의삭제
	@GetMapping("/classList/delete")
	public String deleteClass(@RequestParam(value = "clsId") int clsId, RedirectAttributes rttr, Locale locale) { 
 
		log.debug("삭제 요청 id : {}", clsId);
		classService.deleteClass(clsId);
		rttr.addFlashAttribute("successMessage", messageSource.getMessage("deleteClass", null, locale));

		return "redirect:/admin/classList"; // clsId
	}
	
	

	// 강의수정
	/*
	 * @GetMapping("/classUpdate") public String updateClass(@RequestParam int
	 * clsId, Model model) {
	 * 
	 * model.addAttribute("classList", classService.selectClassList(clsId));
	 * 
	 * return "admin/class/updateClass"; }
	 */

	
	
	
	
	
	
	
	
	
	
	
	
	
	
} // class
