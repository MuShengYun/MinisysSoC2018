.data 0x0000
    table:.word 0x138,0x140,0x148,0x150,0x170,0x178,0x184      #中断向量表(中断判断程序首地址为0x100)

.text 0x0000
start:  addi    $gp,$zero,0             #初始化$gp寄存器
        addi    $sp,$zero,0xff00        #初始化$sp寄存器
        addi    $t0,$zero,0
        addi    $t1,$zero,0x0014
        lui     $t2,0x55aa
        xori    $t2,$t2,0x55aa
        lui     $t3,0x0aa55
        xori    $t3,$t3,0x0aa55
        addi    $s0,$zero,0x0e1         #RAM错误码
        addi    $s1,$zero,1
chkram: lw      $t8,0($t0)
        sw      $t2,0($t0)
        lw      $t4,0($t0)
        beq     $t4,$t2,1               #跳转到error
        j       error
        sw      $t3,0($t0)
        lw      $t4,0($t0)
        beq     $t4,$t3,1               #跳转到error
        j       error
        sw      $t8,0($t0)              #恢复原数值
        beq     $t0,$t1,2               #检查完毕
        addi    $t0,$t0,0x1             #到下一个地址
        j       chkram
chkkey: lh      $t5,0xfc12($zero)       #读键盘状态
        andi    $t0,$t5,0x1         
        beq     $t0,$zero,1             #没有按键时读取按键信息，跳转到error
        j       error
chkled: addi    $t0,$zero,0
        xori    $t1,$zero,0x0aaaa
led:    addi    $t2,$zero,2
        sh      $s1,0xfc60($zero)
loop:   addi    $t2,$t2,-1
        bne     $t2,$zero,-3
        addi    $t0,$t0,0x0457
        bne     $t0,$t1,-6              #跳转到led标签
        j       code                    #启动用户程序
error:  sh      $s0,0xfc00($zero)       #在数码管上显示错误码
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        andi    $t6,$zero,0             #nop（保留指令，无意义）
        j       error
intupt: addi    $t0,$zero,12            #关中断
        mfc0    $t1,$t0,0
        andi    $t2,$t1,0xfffffffe
        mtc0    $t2,$t0,1               #关中断完毕
        addi    $t0,$zero,15            #中断判断程序
        mfc0    $t1,$t0,0               #将15号Base寄存器中的值(中断向量号)读取放入t1
        sll     $t3,$t1,2               #将$t1的值左移两位（乘四），得到的值放入$t3，即为中断向量表对应的地址
        lw      $t2,0($t3)              #以t3寄存器的内容为基地址，从此地址中读取出中断处理程序入口地址放入t2
        jalr    $ra,$t2                 #跳转到对应中断处理程序,并保存下一条指令地址到$ra寄存器
        addi    $t0,$zero,12            #开中断
        mfc0    $t1,$t0,0
        ori     $t2,$t1,1
        mtc0    $t2,$t0,1               #开中断完毕
        eret
tube:   sh      $k0,0($k1)              #数码管中断处理程序
        jr      $ra
switch: lh      $k0,0xfc70($zero)       #拨码开关中断处理程序
        jr      $ra
watdog: sh      $k0,0xfc50($zero)       #看门狗中断处理程序
        jr      $ra
key:    lh      $k0,0xfc10($zero)       #(4*4)键盘中断处理程序
        lh      $k1,0xfc12($zero)       #读键盘状态寄存器
        addi    $t0,$k1,0
        andi    $t1,$t0,0x1
        addi    $t2,$zero,0x1
        beq     $t1,$t2,0x1
        j       key
        jr      $ra
light:  sh      $k0,0xfc60($zero)       #LED灯中断处理程序
        jr      $ra
zero_e: addi    $t0,$zero,0xe           #除零异常处理(数码管输出e)
        sh      $t0,0xfc00($zero)
        jr      $ra
over_e: addi    $t0,$zero,0xf           #加减溢出异常处理（数码管输出f）
        sh      $t0,0xfc00($zero)
        jr      $ra
code:   addi    $k1,$zero,0x03e8
        sh      $k1,0xfc30($zero)
        addi    $k1,$zero,0x01f4
        sh      $k1,0xfc32($zero)
        addi    $k1,$zero,1
        sh      $k1,0xfc34($zero)       #PWM
        syscall 1                       #读取拨码开关的值
        addi    $k1,$zero,0xfc04        #端口号为特殊显示寄存器
        syscall 0                       #将拨码开关的值写入数码管特殊显示寄存器
        addi    $k0,$zero,0x0aaa        #猜数游戏开始
        addi    $k1,$zero,0xfc00
        syscall 0                       #数码管输出为aaa,表示游戏开始
        syscall 3                       #键盘读取设定数
        addi    $s2,$k0,0               #保存设定数
begin:  syscall 3                       #读取玩家输入的值
        addi    $k1,$zero,0xfc00
        syscall 0                       #显示玩家输入的值
        addi    $t1,$k0,0               #保存玩家输入的值
        bne     $s2,$t1,3               #不相等则跳转
        addi    $k0,$zero,0xffff        #相等则16位LED灯全亮,游戏结束
        syscall 4
        beq     $zero,$zero,-3
        jg      $s2,$t1,3               #玩家值小于设定值，跳转
        addi    $k0,$zero,0x8000        #玩家值大于设定值，LED灯最高位亮
        syscall 4
        j       begin
        addi    $k0,$zero,0x1           #玩家值小于设定值，LED灯最低位亮
        syscall 4
        j       begin
        



