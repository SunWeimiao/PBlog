package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Menu;
import com.swm.domain.vo.MenuTreeVo;
import com.swm.domain.vo.MenuVo;
import com.swm.domain.vo.RoleMenuTreeSelectVo;
import com.swm.service.MenuService;
import com.swm.utils.BeanCopyUtils;
import com.swm.utils.SystemConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/menu")
@Api("菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/treeselect")
    @ApiOperation("获取菜单下拉树列表")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }


    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    @ApiOperation("加载对应角色菜单列表树")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }

    @GetMapping("/list")
    @ApiOperation("获取菜单列表")
    public ResponseResult list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @PostMapping
    @ApiOperation("新增菜单")
    public ResponseResult add(@RequestBody Menu menu)
    {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping(value = "/{menuId}")
    @ApiOperation("根据菜单编号获取详细信息")
    public ResponseResult getInfo(@PathVariable Long menuId)
    {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    @PutMapping
    @ApiOperation("修改菜单")
    public ResponseResult edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }


    @DeleteMapping("/{menuId}")
    @ApiOperation("删除菜单")
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChild(menuId)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }

}
